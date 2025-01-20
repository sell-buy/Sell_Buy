package com.sell_buy.sell_buy.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("/")
    public ModelAndView test() {
        System.out.println("Hello World");
        return new ModelAndView("index");
    }
}
