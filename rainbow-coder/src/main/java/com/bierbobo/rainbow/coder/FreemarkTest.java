package com.bierbobo.rainbow.coder;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*

FreeMarker支持如下转义字符:
\";双引号(u0022)
\';单引号(u0027)
\\;反斜杠(u005C)
\n;换行(u000A)
\r;回车(u000D)
\t;Tab(u0009)
\b;退格键(u0008)
\f;Form feed(u000C)
\l;<
\g;>
\a;&
\{;{
\xCode;直接通过4位的16进制数来指定Unicode码,输出该unicode码对应的字符.

如果某段文本中包含大量的特殊符号,FreeMarker提供了另一种特殊格式:可以在指定字符串内容的引号前增加r标记,在r标记后的文件将会直接输出.看如下代码:
${r"${foo}"}
${r"C:\foo\bar"}


 */

public class FreemarkTest {

    public static void main(String[] args) {


        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
//        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(FreemarkTest.class, "/template"));
//        configuration.setDefaultEncoding("UTF-8");


        try {
            Template template = configuration.getTemplate("/RuleCategoryLimitMapper.ftl");


            String[] split = "inStockNum,inStockNumCost,transferInNum,ztNum,transferOutNum,numOrderBooking,numAppBooking,numNosale,availableStock,notArriveStock,canReserveNum,numOrderTransfer".split(",");

            List<Map> list = new ArrayList<Map>();
            for (String field : split) {
                Map map = new HashMap<>();
                String method = field.substring(0, 1).toUpperCase() + field.substring(1);
                map.put("method", method);
                map.put("field", field);
                list.add(map);
            }

            Map<String, Object> context = new HashMap<String, Object>();
            context.put("data", list);

//            StringWriter writer = new StringWriter();
//            template.process(context, writer);
//            System.out.println(writer.toString());

            // 合并数据模型模板
            Writer out = new OutputStreamWriter(System.out);
            template.process(context, out);
            out.flush();
            out.close();

        } catch ( Exception e) {
            e.printStackTrace();
        }

    }

    private static void test1() {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
//        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(FreemarkTest.class, "/template"));
//        configuration.setDefaultEncoding("UTF-8");


        try {
            Template template = configuration.getTemplate("/getMethod.ftl");


            String[] split = "inStockNum,inStockNumCost,transferInNum,ztNum,transferOutNum,numOrderBooking,numAppBooking,numNosale,availableStock,notArriveStock,canReserveNum,numOrderTransfer".split(",");

            List<Map> list = new ArrayList<Map>();
            for (String field : split) {
                Map map = new HashMap<>();
                String method = field.substring(0, 1).toUpperCase() + field.substring(1);
                map.put("method", method);
                map.put("field", field);
                list.add(map);
            }

            Map<String, Object> context = new HashMap<String, Object>();
            context.put("data", list);

//            StringWriter writer = new StringWriter();
//            template.process(context, writer);
//            System.out.println(writer.toString());

            // 合并数据模型模板
            Writer out = new OutputStreamWriter(System.out);
            template.process(context, out);
            out.flush();
            out.close();

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
