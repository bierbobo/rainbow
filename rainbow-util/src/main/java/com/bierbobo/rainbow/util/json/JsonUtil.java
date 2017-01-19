package com.bierbobo.rainbow.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lifubo on 2016/11/15.
 */
public class JsonUtil {

    public static List<Map> generateData() throws IOException {

        List<Map> resultlist=new ArrayList<>();

        String jsonFile="/json/dc.json";
        InputStream resourceAsStream = JsonParse.class.getResourceAsStream(jsonFile);
        String jsonstr = IOUtils.toString(resourceAsStream, "utf-8");
        Map<String,Map<String,String>> jsonMap= (Map)  JSON.parseObject(jsonstr, Feature.OrderedField);


        for (Map.Entry<String, Map<String, String>> entry : jsonMap.entrySet()) {

            Map<String,Object> map=new HashMap<>();

            String field = entry.getKey();

            Map<String, String> valueMap = entry.getValue();
            String sonfield = valueMap.get("field");
            String[] sonfieldArr = sonfield.split(",");
            List<String> list=new ArrayList<>();
            for (int i = 0; i < sonfieldArr.length; i++) {
                String s = sonfieldArr[i].substring(0, 1).toUpperCase() + sonfieldArr[i].substring(1);
                list.add("set"+  s  );
            }

            String method ="set"+ field.substring(0, 1).toUpperCase() + field.substring(1);

            map.put("field",field);
            map.put("method",method);
            map.put("args",field);
            map.put("array",list);

            resultlist.add(map);
        }

        return resultlist;

    }
}
