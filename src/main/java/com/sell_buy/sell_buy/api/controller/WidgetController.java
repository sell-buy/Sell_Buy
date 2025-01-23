package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequestMapping(value = "/payment")
@RequiredArgsConstructor
@Controller
public class WidgetController {
    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderService orderService;

    @GetMapping("/{prodId}")
    public ModelAndView processPayment(@PathVariable("prodId") long prodId,
                                       @RequestParam("prodName") String prodName,
                                       @RequestParam("price") String price) {
        Member member = authenticationService.getAuthenticatedMember();
        // 받은 값 처리
        System.out.println(prodId);
        System.out.println("Product name: " + prodName);
        System.out.println("Price: " + price);
        // 결제 페이지로 이동
        ModelAndView modelAndView = new ModelAndView("payment_checkout");
        modelAndView.setViewName("payment_checkout");
        modelAndView.addObject("prodId", prodId);
        modelAndView.addObject("memId", member.getMemId());
        modelAndView.addObject("prodName", prodName);
        modelAndView.addObject("price", price);
        modelAndView.addObject("email", member.getEmail());
        modelAndView.addObject("phone", member.getPhoneNum());
        modelAndView.addObject("address", member.getAddress());
        modelAndView.addObject("memName", member.getName());
        return modelAndView;
    }

    @GetMapping(value = "/success")
    public String success() {
        return "payment_success";
    }

    @PostMapping("/payment/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {
        JSONParser parser = new JSONParser();
        String orderId; //난수
        String amount; // 금액
        String paymentKey;
        String prodName;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
            prodName = (String) requestData.get("prodName");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        if (code == 200) {
            System.out.println("결제 성공했습니다!");
            //결제 성공시
            return ResponseEntity.status(code).body(jsonObject);
        } else {
            return ResponseEntity.status(code).body(jsonObject);
        }


    }
}