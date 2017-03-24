package com.bierbobo.rainbow.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {

    @RequestMapping("/hello")

    public String hello(Model model) {

        model.addAttribute("username", "张三");

        return "hello";

    }

    @RequestMapping("/world")
    public String helloworld(Model model) {

        model.addAttribute("username","李四");

        return "world";

    }

//    HttpServletRequest request,HttpServletResponse response
    @RequestMapping(value="vm")
    public ModelAndView printWelcome() {
        ModelAndView mav= new ModelAndView();
        mav.addObject("city","test");
        mav.setViewName("hello");
        return mav;
    }


}