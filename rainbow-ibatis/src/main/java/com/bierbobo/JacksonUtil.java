package com.bierbobo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**

 https://github.com/FasterXML/jackson/


 Jackson可以轻松的将Java对象转换成json对象和xml文档，同样也可以将json、xml转换成Java对象。
 官网：http://wiki.fasterxml.com/JacksonHome



 jackson-core：核心包
 jackson-annotations：注解包
 jackson-databind：数据绑定包
 jackson-dataformat-xml

 @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
 @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-study")。
 @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。

 */

/**
 * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
 * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
 * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
 * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
 * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
 * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
 */


public class JacksonUtil {


    public static String Array2Json(Object array) throws RuntimeException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:study"));
            result = mapper.writeValueAsString(array);
        } catch (Exception e) {
            //log.error("JacksonUtil Exception>>>>>>>:{}", e.toString());
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String wrapSuccessString(String status, String jsonSource) {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode node = mapper.createObjectNode();
            node.put("status", status);
            node.put("value", mapper.readTree(jsonSource));

            result = mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

//    ArrayNode arr = mapper.createArrayNode();
}
