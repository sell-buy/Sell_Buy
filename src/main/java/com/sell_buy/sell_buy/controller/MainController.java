package com.sell_buy.sell_buy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String test() {
        System.out.println("Hello World");
        return "index";
    }
}
