package com.bierbobo.rainbow.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by lifubo on 2016/9/20.
 */
public class JsonParse {

    public static void main(String[] args) {


        //  #(.*)  装换成  "comment":"$1",
        Formatter formatter=new Formatter(System.out);

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream("/mapping.json");
        String jsonstr = ReadFileToString(resourceAsStream);

//        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr,Feature.OrderedField );
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr );

        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            String field = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String type = valueMap.get("type");
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            String comment = valueMap.get("comment");
            formatter.format("private %s %s;//%s \n", type, field,comment);
        }

        formatter.flush();
        formatter.close();

        /*

*/


/*        String text = "{ // aa" //
                + "\n\"value\":1001" +
                "*//* aa *//* }";
        JSONObject obj = (JSONObject) JSON.parse(text);
        System.out.println(obj);

        test1();*/


//
//        List<Map<String, String>> json = file2Json();
//
//        for (Map<String, String> dbMap : json) {
//            String key = dbMap.get("key");
//            System.out.println(key);
//        }

//        String jsonWithComment = "{ /*tes****\n\r\n*t*/\"a\":1 /*****test88888*****/ /*test*/ , /*test*/ //test\n //est\n \"b\":2}";
//        JSONObject object = JSON.parseObject(jsonWithComment, Feature.AllowComment);
//        System.out.println(object.toJSONString());


    }

    private static void test1() {

        String jsonString = "{  // sdfs \n"  +
                " \"skuId\": {    \"type\": \"long\",    \"index\": \"not_analyzed\"  },  \"skuId1\": {        \"type\": \"long\",        \"index\": \"not_analyzed\"      }}  ";

        LinkedHashMap<String, LinkedHashMap<String,String>>  parse=    JSON.parseObject(jsonString,
                new TypeReference<LinkedHashMap<String, LinkedHashMap<String, String>>>() {
                }
//                ,Feature.AllowComment
        );


        for (Map.Entry<String, LinkedHashMap<String, String>> objectEntry : parse.entrySet()) {

            Map<String, String> jsonMap=new LinkedHashMap<>();

            String key = objectEntry.getKey();
            LinkedHashMap<String, String>   value =  objectEntry.getValue();

            jsonMap.put("key",key);
            jsonMap.put("json_type", value.get("type").toString());
            jsonMap.put("json_index",value.get("index").toString());


            System.out.println(jsonMap);

        }
    }


    private static void file2Json1() {

        List<Map<String, String>> jsonMapList = new ArrayList<>();

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream("/mapping.json");
        String jsonstr = ReadFileToString(resourceAsStream);

//        Map map1= (Map)  JSON.parseObject(jsonstr,Feature.OrderedField );
        Map map1= (Map)  JSON.parseObject(jsonstr );
        System.out.println(map1);

/*

       JSONObject jsonObject=JSON.parseObject(jsonstr,Feature.OrderedField );

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {

            String key = entry.getKey();
            JSONObject value = (JSONObject) entry.getValue();
            System.out.println(value);

            Map map= (Map) entry.getValue();
            System.out.println(map);
//            for (Map.Entry<String, Object> valueEntry : value.entrySet()) {
//                System.out.println(valueEntry.getKey());
//            }
        }
*/

    }


    private static List<Map<String, String>> file2Json() {

        List<Map<String, String>> jsonMapList = new ArrayList<>();

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream("/mapping.json");
        System.out.println(resourceAsStream);

        String jsonstr = ReadFileToString(resourceAsStream);
        LinkedHashMap<String, LinkedHashMap<String,String>>  parse=    JSON.parseObject(jsonstr,
                new TypeReference<LinkedHashMap<String, LinkedHashMap<String, String>>>() {}
//                ,Feature.AllowComment
        );


        for (Map.Entry<String, LinkedHashMap<String, String>> objectEntry : parse.entrySet()) {

            Map<String, String> jsonMap=new LinkedHashMap<>();

            String key = objectEntry.getKey();
            LinkedHashMap<String, String>   value =  objectEntry.getValue();

            jsonMap.put("key",key);
            jsonMap.put("json_type", value.get("type").toString());
            jsonMap.put("json_index",value.get("index").toString());


            jsonMapList.add(jsonMap);

        }

        return jsonMapList;
    }


    public static String ReadFileToString(InputStream fis)
    {

        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //构建成String
        StringBuffer sb = new StringBuffer();
        String temp = null;
        try
        {
            while((temp = br.readLine()) != null)
            {
                if(temp.trim().length()>0){
                    sb.append(temp+'\n');
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
