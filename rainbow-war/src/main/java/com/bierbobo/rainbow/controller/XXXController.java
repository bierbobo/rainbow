package com.bierbobo.rainbow.controller;


import com.bierbobo.rainbow.domain.common.Page;
import com.bierbobo.rainbow.domain.entity.Entity;
import com.bierbobo.rainbow.domain.vo.QueryVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lifubo on 2016/11/1.
 */

@Controller
@RequestMapping("/ware")
public class XXXController extends BaseController {


    @RequestMapping("/queryList")
    @ResponseBody
    public  Page<Entity> queryWareList(QueryVO queryVO){

        return null;
    }



}
