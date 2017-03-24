package com.bierbobo.rainbow.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by lifubo on 2016/11/1.
 */

@RequestMapping("/base")
@Controller
public class BaseController {

    @ModelAttribute
    public void modelAttribute(Model model) {
        model.addAttribute("age","12");
        model.addAttribute("string","122222");
    }


    @RequestMapping(value = "/index1")
    public void index3(Model model, @ModelAttribute String age,@RequestParam(required=false) String name1, User user11, HttpServletRequest request) {

        System.out.println(model.asMap());
        System.out.println(age);
        model.addAttribute("name", "1234===");
        user11.setUsername("111");

//        sss(request);
    }


    @RequestMapping(value = "/world")
    public void world(Model model, @ModelAttribute String age, User user11, HttpServletRequest request) {

        System.out.println(model.asMap());
        System.out.println(age);
        model.addAttribute("name", "1234===");
        user11.setUsername("111");

//        sss(request);

    }

    private void sss(HttpServletRequest request) {
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            System.out.println(name+":"+value);
        }

        System.out.println("===========================================================");

        Enumeration   e   =   request.getAttributeNames();
        while( e.hasMoreElements())   {
            String sessionName=(String)e.nextElement();
            System.out.println("request item name="+sessionName+"||||value="+request.getAttribute(sessionName));
        }

        e   =   request.getSession().getAttributeNames();
        while( e.hasMoreElements())   {
            String sessionName=(String)e.nextElement();
            System.out.println("Session item name="+sessionName+"==== value="+request.getSession().getAttribute(sessionName));
        }

        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }


    @RequestMapping(value = "/index5")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.setViewName("/user/index");
        modelAndView.addObject("name", "xxx");
        return modelAndView;
    }



    @RequestMapping(value = "/index122")
    public ModelMap index31() {

        System.out.println("1111122");
        ModelAndView modelAndView = new ModelAndView("index2");
        modelAndView.setViewName("index2");
        modelAndView.addObject("name", "xxx");

        ModelMap modelMap = new ModelMap();
//        modelMap.put("name", "111111");
        //map.put相当于request.setAttribute方法
        return modelMap;
    }


    @RequestMapping(value = "/index4")
    public String index(Model model) {
        String retVal = "/index";
        User user = new User(1, "22");
        model.addAttribute("user", user);
        return retVal;
    }

    @RequestMapping("/index")
    public String goIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "xxx");

        return "index";
    }

    @RequestMapping("/index/{variable1}")
    public String goIndex1(@PathVariable String variable1) {
        System.out.println(variable1);
        return "index";
    }

    @RequestMapping("/{variable1}/a")
    public String goIndex12(@PathVariable String variable1) {
        System.out.println(variable1);
        return "index";
    }


    /*

        protected boolean checkParam(QueryVO queryVO){
            return true;
        }


        protected void initParam(QueryVO queryVO){
        }

        public static String convertMD5(String inStr){

            char[] a = inStr.toCharArray();
            for (int i = 0; i < a.length; i++){
                a[i] = (char) (a[i] ^ 't');
            }
            String s = new String(a);
            return s;

        }

    */


}
