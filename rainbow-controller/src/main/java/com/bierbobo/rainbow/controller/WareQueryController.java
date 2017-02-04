package com.bierbobo.rainbow.controller;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by lifubo on 2017/1/29.
 */
@Controller
@RequestMapping("/ware")
public class WareQueryController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/wareDetail/{wareId}")
    public String wareDetail(@PathVariable("wareId") String wareIdMD5 , ModelMap map) {
        try {

            System.out.println("sdfffffffffffffffffff======");
        } catch (Exception e) {
            logger.error("wareDetail--日期转换错误"+e);
        }
        return "wareDetail";
    }


    @RequestMapping("/vm")
    public String wareDetail() {
        try {
            System.out.println("sdfffffffffffffffffff======");
        } catch (Exception e) {
            logger.error("wareDetail--日期转换错误"+e);
        }
        return "vmDemo";
    }

    @RequestMapping("/jsp")
    public String aaa() {
        try {
            System.out.println("11111======");
        } catch (Exception e) {
            logger.error("wareDetail--日期转换错误"+e);
        }
        return "jspDemo";
    }
}
