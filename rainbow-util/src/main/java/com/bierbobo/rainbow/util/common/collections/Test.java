package com.bierbobo.rainbow.util.common.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lifubo on 2016/11/17.
 */
public class Test {

    public static void main(String[] args) {

        List<String> widList=new ArrayList<String>();
        widList.add("1");
        widList.add("2");
        widList.add("3");
        widList.add("4");


        for(Iterator<String> iterator = widList.iterator();iterator.hasNext();){
            String next = iterator.next();
            if("3".equalsIgnoreCase(next)){
                iterator.remove();
            }
        }
        System.out.println(widList);
    }
}
