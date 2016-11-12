package com.bierbobo.rainbow.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lifubo on 2016/9/20.
 */
public class JsonParse {

    public static void main(String[] args) throws IOException {





        //  #(.*)  装换成  "comment":"$1",

//        generalFieldSimple();
//        generateField();
//        generateFieldValue();
//        generateXml1();

    /*    JSONObject topics=new JSONObject();
        topics.put("name","商品");

        JSONArray measureGroups=new JSONArray();

        JSONObject measure=new JSONObject();


        JSONArray measures=new JSONArray();
        measures.add("商品编码");
        measures.add("商品编码1");

        measure.put("name","商品信息");
        measure.put("measures",measures);

        topics.put("measureGroups",measure);
        System.out.println(JSON.toJSONString(topics, true));*/

//        generalJson("dc");
//        generalJsonMap();
    }





    public static void generalJson(String type){

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream("/field.xml");
        SAXReader sr = new SAXReader();
        try {
            Document doc  =  sr.read(resourceAsStream);
            Element configElement = doc.getRootElement();


            JSONArray topicArr=new JSONArray();

            for (Iterator configIterator = configElement.elementIterator();configIterator.hasNext();) {

                Element topicElement = (Element) configIterator.next();

                JSONObject topicJson=new JSONObject();
                topicJson.put("name", topicElement.attributeValue("name"));

                JSONArray measureArr=new JSONArray();

                for (Iterator measureIterator = topicElement.elementIterator();measureIterator.hasNext();) {

                    Element measureElement = (Element) measureIterator.next();
                    JSONObject measure=new JSONObject();
                    measure.put("name",measureElement.attributeValue("name"));

                    JSONArray measures=new JSONArray();
                    for (Iterator field = measureElement.elementIterator();field.hasNext();) {

                        Element fieldElement = (Element) field.next();
                        String typeStr = fieldElement.attributeValue("type");
                        if(type.equalsIgnoreCase(typeStr)||"sku".equalsIgnoreCase(typeStr)||"dc,rdc".equalsIgnoreCase(typeStr)){
                            measures.add(fieldElement.attributeValue("name"));
                        }

                    }
                    measure.put("measures",measures);

                    measureArr.add(measure);
                }

                topicJson.put("measureGroups", measureArr);
                topicArr.add(topicJson);
            }

            System.out.println(JSON.toJSONString(topicArr, true));

        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }



    private static void generateXml1() throws IOException {


        Formatter formatter=new Formatter(System.out);
        String jsonFile="/json/dc_main.json";
        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);

        int count=0;

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");

//            formatter.format("%s \n", comment);
            formatter.format("<field  key=\"%s\" name=\"%s\" type=\"dc\"/> \n",field,comment);

        }
        System.out.println("===================="+count);
        formatter.flush();
    }

    private static void generateXml() throws IOException {

        Map<String,String> map=new HashMap<>();



        Formatter formatter=new Formatter(System.out);
        String jsonFile="/json/dc.json";
        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);

        int count=0;

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");

            formatter.format("%s \n", comment);

            String sonfield = valueMap.get("field");
            String value = valueMap.get("value");


            String[] sonfieldArr = sonfield.split(",");
            String[] valueArr = value.split(",");


            for (int i = 0; i < sonfieldArr.length; i++) {
                count++;
                formatter.format("<field  key=\"%s\" name=\"%s\" type=\"dc\"/> \n",field+sonfieldArr[i],valueArr[i]);
            }

            formatter.format("\n");

        }
        System.out.println("===================="+count);
        formatter.flush();
    }



    private static void generateFieldValue() throws IOException {

        Formatter formatter=new Formatter(System.out);
        String jsonFile="/json/dc.json";
        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);

        int count=0;

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");

            formatter.format("%s \n", comment);

            String sonfield = valueMap.get("field");
            String value = valueMap.get("value");


            String[] sonfieldArr = sonfield.split(",");
            String[] valueArr = value.split(",");


            for (int i = 0; i < sonfieldArr.length; i++) {
                count++;
//                formatter.format("%s \t",valueArr[i]);
            }

            formatter.format("\n");

        }
        System.out.println("===================="+count);
        formatter.flush();
    }



    private static void generalFieldSimple() throws IOException {
        String jsonFile="/json/sku_info.json";
        generalField(jsonFile);

        System.out.println("======================");

        jsonFile="/json/dc_main.json";
        generalField(jsonFile);

        System.out.println("======================");

        jsonFile="/json/dc_ext.json";
        generalField(jsonFile);

        System.out.println("======================");
    }

    private static void generateField() throws IOException {

        Formatter formatter=new Formatter(System.out);
        String jsonFile="/json/dc.json";
        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");

            formatter.format("//%s \n", comment);
            formatter.format("private %s %s;//%s \n", type, field,comment);

            String sonfield = valueMap.get("field");
            String value = valueMap.get("value");


            String[] sonfieldArr = sonfield.split(",");
            String[] valueArr = value.split(",");


            for (int i = 0; i < sonfieldArr.length; i++) {
                formatter.format("private %s %s;//%s \n", type, field+sonfieldArr[i],valueArr[i]);
            }

            formatter.format("\n");

        }

        formatter.flush();
    }


    private static void generalField(String jsonFile) throws IOException {
        Formatter formatter=new Formatter(System.out);

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");

        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");
            formatter.format("private %s %s;//%s \n", type, field,comment);
        }

        formatter.flush();
//        formatter.close();
    }


}
