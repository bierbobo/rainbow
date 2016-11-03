package com.bierbobo.rainbow.controller;

import com.bierbobo.rainbow.domain.vo.QueryVO;
import org.springframework.util.CollectionUtils;

/**
 * Created by lifubo on 2016/11/1.
 */
public class BaseController {


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


}
