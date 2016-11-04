package com.bierbobo.rainbow.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lifubo on 2016/9/20.
 */
public class JsonParse {

    public static void main(String[] args) {

        List<Map<String, String>> json = json();

        for (Map<String, String> dbMap : json) {

            String key = dbMap.get("key");
            System.out.println(key);


        }


    }



    private static List<Map<String, String>> json() {

        List<Map<String, String>> jsonMapList = new ArrayList<>();

        InputStream resourceAsStream = JsonParse.class.getResourceAsStream("mapping.json");
        System.out.println(resourceAsStream);

        String jsonstr = ReadFileToString(resourceAsStream);
//        System.out.println(jsonstr);
//        JSONObject jsonObject = JSON.parseObject(jsonstr, Feature.AutoCloseSource,Feature.AllowComment,Feature.AllowUnQuotedFieldNames,Feature.AllowSingleQuotes,Feature.InternFieldNames,Feature.AllowISO8601DateFormat,Feature.AllowArbitraryCommas,Feature.UseBigDecimal,Feature.IgnoreNotMatch,Feature.SortFeidFastMatch,Feature.DisableASM,Feature.DisableCircularReferenceDetect,Feature.InitStringFieldAsEmpty,Feature.SupportArrayToBean);
//        System.out.println(jsonObject);

        LinkedHashMap<String, LinkedHashMap<String,String>>  parse=    JSON.parseObject(jsonstr, new TypeReference<LinkedHashMap<String, LinkedHashMap<String, String>>>() {
        });

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
