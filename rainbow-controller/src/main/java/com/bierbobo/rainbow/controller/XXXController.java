package com.bierbobo.rainbow.controller;

//import com.bierbobo.rainbow.domain.common.Page;
//import com.bierbobo.rainbow.domain.vo.QueryVO;
import com.bierbobo.rainbow.domain.entity.Entity;
import com.bierbobo.rainbow.service.BaseService;
import com.bierbobo.rainbow.service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lifubo on 2016/11/1.
 */

@Controller
@RequestMapping("/ware111")
public class XXXController extends BaseController {


//    @Autowired(required = false)


    @Autowired
    @Qualifier("X1Service")
    public BaseService baseService;

/*
    @RequestMapping("/queryList")
    @ResponseBody
    public  Page<Entity> queryWareList(QueryVO queryVO){

        return null;
    }

*/


}
