//package com.bierbobo;
//
//import java.io.IOException;
//
//import java.io.StringWriter;
//
//import java.util.ArrayList;
//
//import java.util.HashMap;
//
//import java.util.Iterator;
//
//import java.util.LinkedHashMap;
//
//import java.util.List;
//
//import java.util.Map;
//
//import java.util.Set;
//
//import org.fasterxml.jackson.JsonEncoding;
//
//import org.fasterxml .jackson.JsonGenerationException;
//
//import org.fasterxml .jackson.JsonGenerator;
//
//import org.fasterxml .jackson.JsonParseException;
//
//import org.fasterxml .jackson.map.JsonMappingException;
//
//import org.fasterxml .jackson.map.ObjectMapper;
//
//import org.fasterxml .jackson.node.JsonNodeFactory;
//
//import org.fasterxml .jackson.xml.XmlMapper;
//
//import org.junit.After;
//
//import org.junit.Before;
//
//import org.junit.Test;
//
//import com.hoo.entity.AccountBean;
//
//import java.io.IOException;
//
//
///**
//
// * <b>function:</b>Jackson 将java对象转换成JSON字符串，也可以将JSON字符串转换成java对象
//
// * jar-lib-version: jackson-all-1.6.2
//
// * jettison-1.0.1
//
// * @author hoojo
//
// * @createDate 2010-11-23 下午04:54:53
//
// * @file JacksonTest.java
//
// * @package com.hoo.test
//
// * @project Spring3
//
// * @blog http://blog.csdn.net/IBM_hoojo
//
// * @email hoojo_@126.com
//
// * @version 1.0
//
// */
//
//@SuppressWarnings("unchecked")
//
//public class JacksonTest {
//
//    private JsonGenerator jsonGenerator = null;
//
//    private ObjectMapper objectMapper = null;
//
//    private AccountBean bean = null;
//
//
//
//    @Before
//
//    public void init() {
//
//        bean = new AccountBean();
//
//        bean.setAddress("china-Guangzhou");
//
//        bean.setEmail("hoojo_@126.com");
//
//        bean.setId(1);
//
//        bean.setName("hoojo");
//
//
//
//        objectMapper = new ObjectMapper();
//
//        try {
//
//            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//
//
//    @After
//
//    public void destory() {
//
//        try {
//
//            if (jsonGenerator != null) {
//
//                jsonGenerator.flush();
//
//            }
//
//            if (!jsonGenerator.isClosed()) {
//
//                jsonGenerator.close();
//
//            }
//
//            jsonGenerator = null;
//
//            objectMapper = null;
//
//            bean = null;
//
//            System.gc();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//}