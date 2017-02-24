package com.bierbobo.rainbow.util.vm;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import junit.framework.Test;

/**
 * Created by lifubo on 2016/11/15.
 */
public class MainTest1 {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(MainTest1.class));
        try {
            Template template = configuration.getTemplate("getMethod.ftl");
            StringWriter writer = new StringWriter();


            String[] split =  "inStockNum,inStockNumCost,transferInNum,ztNum,transferOutNum,numOrderBooking,numAppBooking,numNosale,availableStock,notArriveStock,canReserveNum,numOrderTransfer".split(",");

            List<Map> list=new ArrayList<Map>();
            for (String field : split) {
                Map map=new HashMap<>();
                String method = field.substring(0, 1).toUpperCase() + field.substring(1);
                map.put("method",method);
                map.put("field",field);
                list.add(map);
            }
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("data", list);
            template.process(context, writer);

            System.out.println(writer.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
