package com.sell_buy.sell_buy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sell_buy.sell_buy.api.controller.OrderController;
import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.api.service.impl.OrderServiceImpl;
import com.sell_buy.sell_buy.db.entity.Delivery;
import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService; // The service containing updateOrderStatus

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CarrierRepository carrierRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrderController orderController;

//    @Before
//    public void setup() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks
//    }

    @BeforeEach()
    public void setup() {
//        OrderServiceImpl orderServiceimpl = new OrderServiceImpl(
//                orderRepository, deliveryRepository, carrierRepository, memberRepository, productRepository
//        );
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        orderService = new OrderServiceImpl(orderRepository, deliveryRepository,
                carrierRepository, memberRepository, productRepository);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        long orderId = 1L;
        int orderStatus = 2; // 테스트에서는 사용되지 않음
        String carrier = "우체국";
        String trackingNo = "11111111111111";
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setCarrier(carrier);
        delivery.setTrackingNo(trackingNo);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);
        orderService.updateOrder(orderId, orderStatus, carrier, trackingNo);
        Mockito.verify(deliveryRepository, Mockito.times(1)).save(Mockito.any(Delivery.class));


//
//        when(orderService.registerOrder(any(Order.class))).thenReturn(order);
//        mockMvc.perform(post("/order/register")
//                        .session(session)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"transferId\":1,\"prodId\":1,\"sellerId\":3,\"buyerId\":2,\"receiverName\":\"test\"," +
//                                "\"receiverAddress\":\"testaddress\",\"receiverPhone\":\"01011111111\",\"orderStatus\":\"배송준비\"," +
//                                "\"createdDate\":\"2023-10-10T10:00:00\"," +
//                                "\"deliveredDate\":\"2023-10-15T10:00:00\" ," +
//                                "\"orderId\":1}"))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().string("1")); // orderId가 반환될 것으로 예상

    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        List<Long> mockOrders = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus("거래중");
        order.setCarrierStatus("배송중");
        Delivery mockDelivery = new Delivery();
        mockDelivery.setOrderId(1L);
        mockDelivery.setCarrier("우체국");
        mockDelivery.setCarrierId("kr.epost");
        mockDelivery.setTrackingNo("1111111111111");
        // Mocking behavior
        when(orderRepository.findAllIds()).thenReturn(mockOrders);
        when(deliveryRepository.findByOrderId(1L)).thenReturn(mockDelivery);
        orderService.updateOrderStatus();
        // null이면 실패
        System.out.println(deliveryRepository.findByOrderId(1L).toString());
        System.out.println(order.getOrderStatus());
        System.out.println(order.getCarrierStatus());
        //orderid= 1 / status = 거래완료
//        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        assertEquals("거래완료 ", order.getOrderStatus());
        assertEquals("배달완료", order.getCarrierStatus());
    }

    @Test
    public void orderControllerTRegister() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memId", 1L);
        Order order = Order.builder()
                .transferId(1L)
                .prodId(1L)
                .sellerId(3L)
                .buyerId(2L)
                .receiverName("test")
                .receiverAddress("testaddress")
                .receiverPhone("01011111111")
                .orderType(2)
                .createdDate(LocalDateTime.now())
                .deliveredDate(LocalDateTime.now())
                .carrierStatus("배송준비")
                .build();

        when(orderService.registerOrder(any(Order.class))).thenReturn(order);
        mockMvc.perform(post("/order/register")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transferId\":1,\"prodId\":1,\"sellerId\":3,\"buyerId\":2,\"receiverName\":\"test\"," +
                                "\"receiverAddress\":\"testaddress\",\"receiverPhone\":\"01011111111\",\"orderStatus\":\"배송준비\"," +
                                "\"createdDate\":\"2023-10-10T10:00:00\"," +
                                "\"deliveredDate\":\"2023-10-15T10:00:00\" ," +
                                "\"orderId\":1}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("1")); // orderId가 반환될 것으로 예상
    }
}

