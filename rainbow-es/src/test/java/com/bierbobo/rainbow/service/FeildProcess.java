package com.bierbobo.rainbow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.bierbobo.rainbow.service.WareData;
import com.bierbobo.rainbow.service.WareSlaveData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lifubo on 2016/9/7.
 */
public class FeildProcess {


    private static Map<String, String> WareDataMap;
    private static Map<String, String> WareSlaveDataMap;


    private static Map<String, List> excludeMap ;
    private static Map<String, String> mappping ;
    private static List<String> querylist = new ArrayList<>();

    public static void main(String[] args) throws  Exception {


        code(FeildProcess.class.getClassLoader().getResourceAsStream("test.properties"));

//        genarator2();


//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/elasticsearchRepository.xml");
//        JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
//
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from lifubo1");
//        System.out.println(maps);






    }



    private static void genarator2() {
        try {

            WareDataMap = convertMap(WareData.class);
            WareSlaveDataMap = convertMap(WareSlaveData.class);
            parseXMLConfig();


            List<Map<String, String>> db = db();
            List<Map<String, String>> json = json();
            List<Map<String, String>> xml = parseXML();

            for (Map<String, String> dbMap : db) {

                String db_field = dbMap.get("db_field");

                Iterator<Map<String, String>> iterator = xml.iterator();
                Map<String, String> xmlMap=null;
                for(;iterator.hasNext();){
                    xmlMap=iterator.next();
                    String xml_field = xmlMap.get("xml_field");

                    if(xml_field!=null&&xml_field.equalsIgnoreCase(db_field)){
                        dbMap.putAll(xmlMap);
                        iterator.remove();

                        break;
                    }
                }

            }


            for (Map<String, String> dbMap : db) {

                String xml_property = dbMap.get("db_field");


                String xml_property_json = mappping.get(xml_property);
                if(xml_property_json!=null){
                    for(Iterator<Map<String, String>> iterator = json.iterator();iterator.hasNext();){
                        Map<String, String> jsonMap=iterator.next();
                        String json_field = jsonMap.get("json_field");
                        if(json_field!=null&&json_field.equalsIgnoreCase(xml_property_json)){
                            dbMap.putAll(jsonMap);
                            iterator.remove();
                            mappping.remove(xml_property);
                            break;
                        }
                    }
                    continue;
                }


                boolean find=false;

                for(Iterator<Map<String, String>> iterator = json.iterator();iterator.hasNext();){
                    Map<String, String> jsonMap=iterator.next();
                    String json_field = jsonMap.get("json_field");
                    if(json_field!=null&&json_field.equalsIgnoreCase(xml_property)){
                        dbMap.putAll(jsonMap);
                        iterator.remove();
                        find=true;
                        break;
                    }
                }

                if(!find){

                    String db_field = dbMap.get("db_field");
                    String[] split = db_field.split("_");
                    StringBuilder sb=new StringBuilder();
                    sb.append(split[0]);
                    for (int i = 1; i < split.length; i++) {
                        sb.append(split[i].substring(0,1).toString()+split[i].substring(1));
                    }

                    String xml_property_new = sb.toString();

                    for(Iterator<Map<String, String>> iterator = json.iterator();iterator.hasNext();){
                        Map<String, String> jsonMap=iterator.next();
                        String json_field = jsonMap.get("json_field");
                        if(json_field!=null&&json_field.equalsIgnoreCase(xml_property_new)){
                            dbMap.putAll(jsonMap);
                            iterator.remove();
                            break;
                        }
                    }
                }
            }

            for (Map<String, String> dbMap : db) {

                String db_field = dbMap.get("db_field");

                for(Iterator<String> iterator = querylist.iterator();iterator.hasNext();){
                    String xmlMap=iterator.next();
                    if(xmlMap!=null&&xmlMap.equalsIgnoreCase(db_field)){
                        dbMap.put("query_field",xmlMap);
                        iterator.remove();
                        break;
                    }
                }

            }



            try {
                HSSFWorkbook workbook= new HSSFWorkbook();

                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                HSSFSheet sheet= workbook.createSheet("field");

                int rowNo=1;

                for (Map<String, String> dbMap : db) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(dbMap.get("db_field"));
                    row.createCell(2).setCellValue(dbMap.get("db_type"));

                    row.createCell(4).setCellValue(dbMap.get("xml_field"));
                    row.createCell(5).setCellValue(dbMap.get("xml_type"));

                    row.createCell(6).setCellValue(dbMap.get("xml_property"));
                    row.createCell(7).setCellValue(dbMap.get("xml_property_type"));


                    row.createCell(9).setCellValue(dbMap.get("json_field"));
                    row.createCell(10).setCellValue(dbMap.get("json_type"));
                    row.createCell(11).setCellValue(dbMap.get("json_index"));
                    if(dbMap.get("xml_property_type")!=null && !dbMap.get("xml_property_type").equalsIgnoreCase(dbMap.get("json_type_java"))){
                        HSSFCell cell = row.createCell(12);
                        cell.setCellValue(dbMap.get("json_type_java"));
                        cell.setCellStyle(style);
                        if(dbMap.get("xml_property_type").equalsIgnoreCase("Integer") && "Long".equalsIgnoreCase(dbMap.get("json_type_java"))){
                            row.createCell(13).setCellValue(dbMap.get("xml_property_type"));
                        }else{
                            HSSFCell cell1 = row.createCell(13);
                            cell1.setCellStyle(style);
                            cell1.setCellValue(dbMap.get("json_type_java_1"));
                        }
                    }else {
                        row.createCell(12).setCellValue(dbMap.get("json_type_java"));
                        row.createCell(13).setCellValue(dbMap.get("json_type_java_1"));
                    }

                    row.createCell(15).setCellValue(dbMap.get("query_field"));




                }

                sheet= workbook.createSheet("xml");
                rowNo=1;
                for (Map<String, String> xmlMap : xml) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(xmlMap.get("xml_field"));
                    row.createCell(2).setCellValue(xmlMap.get("xml_type"));
                    row.createCell(3).setCellValue(xmlMap.get("xml_property"));
                    row.createCell(4).setCellValue(xmlMap.get("xml_property_type"));
                }


                sheet= workbook.createSheet("json");
                rowNo=1;
                for (Map<String, String> jsonMap : json) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(jsonMap.get("json_field"));
                    row.createCell(2).setCellValue(jsonMap.get("json_type"));
                    row.createCell(3).setCellValue(jsonMap.get("json_index"));
                    row.createCell(4).setCellValue(jsonMap.get("json_type_java"));

                }

                sheet= workbook.createSheet("query");
                rowNo=1;
                for (String s : querylist) {
                    HSSFRow row = sheet.createRow(rowNo++);
                    row.createCell(1).setCellValue(s);
                }




                FileOutputStream os = new FileOutputStream("aaa.xls");
                workbook.write(os);
                os.flush();
                os.close();
            } catch ( Exception e) {
                e.printStackTrace();
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    private static void genarator1() {
        try {

            WareDataMap = convertMap(WareData.class);
            WareSlaveDataMap = convertMap(WareSlaveData.class);



            List<Map<String, String>> db = db();
            List<Map<String, String>> json = json();
            List<Map<String, String>> xml = parseXML();




            try {
                HSSFWorkbook workbook= new HSSFWorkbook();



                HSSFSheet sheet= workbook.createSheet("db");

                int rowNo=1;
                for (Map<String, String> dbMap : db) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(dbMap.get("db_field"));
                    row.createCell(2).setCellValue(dbMap.get("db_type"));

                }

                sheet= workbook.createSheet("json");
                rowNo=1;
                for (Map<String, String> jsonMap : json) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(jsonMap.get("json_field"));
                    row.createCell(2).setCellValue(jsonMap.get("json_type"));
                    row.createCell(3).setCellValue(jsonMap.get("json_index"));

                }

                sheet= workbook.createSheet("xml");
                rowNo=1;
                for (Map<String, String> xmlMap : xml) {

                    HSSFRow row = sheet.createRow(rowNo++);

                    row.createCell(1).setCellValue(xmlMap.get("xml_field"));
                    row.createCell(2).setCellValue(xmlMap.get("xml_type"));
                    row.createCell(3).setCellValue(xmlMap.get("xml_property"));

                }


                FileOutputStream os = new FileOutputStream("fisrtExcel.xls");
                workbook.write(os);
                os.flush();
                os.close();
            } catch ( Exception e) {
                e.printStackTrace();
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    private static List<Map<String, String>> db( ) {

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/elasticsearchRepository.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
        String[] tableArray=new String[]{"desc ware","desc sales","desc traffic","desc inoutstore"};

        List<Map<String, String>> dbMapList = new ArrayList<>();

        for (String table : tableArray) {

            Map<String, String> tableName=new HashMap<String,String>();

            tableName.put("db_type","================"+ table);
            tableName.put("db_field", "================"+ table);
            dbMapList.add(tableName);

            List<Map<String, Object>> maps = jdbcTemplate.queryForList(table);
            for (Map<String, Object> map : maps) {

                if(excludeMap.get(table)!=null){
                    if(excludeMap.get(table).contains(map.get("field").toString())){
                        continue;
                    }
                }

                Map<String, String> dbMap=new HashMap<String,String>();
                dbMap.put("db_type", map.get("type").toString());
                dbMap.put("db_field", map.get("field").toString());
                dbMapList.add(dbMap);
            }
        }


        return dbMapList;
    }

    private static   List<Map<String, String>> json() {

        List<Map<String, String>> jsonMapList = new ArrayList<>();

        InputStream resourceAsStream = FeildProcess.class.getResourceAsStream("/mapping.json");
        String jsonstr = ReadFileToString(resourceAsStream);
//        System.out.println(jsonstr);
//        JSONObject jsonObject = JSON.parseObject(jsonstr, Feature.AutoCloseSource,Feature.AllowComment,Feature.AllowUnQuotedFieldNames,Feature.AllowSingleQuotes,Feature.InternFieldNames,Feature.AllowISO8601DateFormat,Feature.AllowArbitraryCommas,Feature.UseBigDecimal,Feature.IgnoreNotMatch,Feature.SortFeidFastMatch,Feature.DisableASM,Feature.DisableCircularReferenceDetect,Feature.InitStringFieldAsEmpty,Feature.SupportArrayToBean);
//        System.out.println(jsonObject);

        LinkedHashMap<String, LinkedHashMap<String,String>>  parse=    JSON.parseObject(jsonstr, new TypeReference<LinkedHashMap<String, LinkedHashMap<String, String>>>() {});

        for (Map.Entry<String, LinkedHashMap<String, String>> objectEntry : parse.entrySet()) {

            Map<String, String> jsonMap=new LinkedHashMap<>();

            String key = objectEntry.getKey();
            LinkedHashMap<String, String>   value =  objectEntry.getValue();

            jsonMap.put("json_field",key);
            jsonMap.put("json_type", value.get("type").toString());
            jsonMap.put("json_index",value.get("index").toString());
            jsonMap.put("json_type_java",convertMap(value.get("type").toString()));
            jsonMap.put("json_type_java_1",convertMap(value.get("type").toString()));

            jsonMapList.add(jsonMap);

        }

        return jsonMapList;
    }

    public static String convertMap(String dbtype){

        String javatype=null;
        if(dbtype.equalsIgnoreCase("long")){
            javatype="Long";
        }else if(dbtype.equalsIgnoreCase("string")){
            javatype="String";
        }else if(dbtype.equalsIgnoreCase("date")){
            javatype="Date";
        }else if(dbtype.equalsIgnoreCase("short")){
            javatype="Integer";
        }else if(dbtype.equalsIgnoreCase("short")){
            javatype="Integer";
        }else if(dbtype.equalsIgnoreCase("integer")){
            javatype="Integer";
        }else if(dbtype.equalsIgnoreCase("double")){
            javatype="Double";
        }
        return javatype;
    }

    public static Map<String,String>  convertMap(Class type)   {

        Map<String,String> map=new HashMap<String,String>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                String simpleName = descriptor.getPropertyType().getSimpleName();

                map.put(propertyName,simpleName);
            }

        } catch (Exception e) {
            e.printStackTrace();;
        }

        return map;

    }

    public static List<Map<String, String>> parseXML(){

        List<Map<String, String>> xmlMapList = new ArrayList<>();

        InputStream resourceAsStream = FeildProcess.class.getResourceAsStream("/mapping.xml");

        SAXReader sr = new SAXReader();
        try {
            Document doc  =  sr.read(resourceAsStream);
            Element resultMap = doc.getRootElement();

            for (Iterator result = resultMap.elementIterator();result.hasNext();) {

                Map<String, String> xmlMap=new HashMap<String,String>();

                Element resultElement = (Element) result.next();

                String property = resultElement.attributeValue("property");

                xmlMap.put("xml_field", resultElement.attributeValue("column"));
                xmlMap.put("xml_type", resultElement.attributeValue("jdbcType"));
                xmlMap.put("xml_property",property );

                String type = WareDataMap.get(property);
                if(type==null){
                    type = WareSlaveDataMap.get(property);
                }
                xmlMap.put("xml_property_type",type );


                xmlMapList.add(xmlMap);

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return xmlMapList;
    }

    public static void parseXMLConfig(){

        excludeMap=new HashMap<>();

        mappping=new HashMap<>();

        List<Map<String, String>> xmlMapList = new ArrayList<>();

        InputStream resourceAsStream = FeildProcess.class.getResourceAsStream("/config.xml");

        SAXReader sr = new SAXReader();
        try {
            Document doc  =  sr.read(resourceAsStream);

            Element rootElement = doc.getRootElement();
            Element tableElement = rootElement.element("exclude").element("table");

            String name = tableElement.attributeValue("name");
            String field = tableElement.attributeValue("field");

            String[] split = field.split(",");
            List list=new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            excludeMap.put(name, list);


            List<Element> elements = rootElement.element("mapping").elements("field");
            for (Element element  : elements) {

                String db_name = element.attributeValue("db_name");
                String mapping_name = element.attributeValue("mapping_name");
                mappping.put(db_name,mapping_name);
            }



            Element query = rootElement.element("query");
            String text = query.getText();
            for (String s : text.split(",")) {
                querylist.add(s.trim());
            }

/*
            Element resultMap =null;


            for (Iterator result = resultMap.elementIterator();result.hasNext();) {

                Map<String, String> xmlMap=new HashMap<String,String>();

                Element resultElement = (Element) result.next();

                String property = resultElement.attributeValue("property");

                xmlMap.put("xml_field", resultElement.attributeValue("column"));
                xmlMap.put("xml_type", resultElement.attributeValue("jdbcType"));
                xmlMap.put("xml_property",property );

                String type = WareDataMap.get(property);
                if(type==null){
                    type = WareSlaveDataMap.get(property);
                }
                xmlMap.put("xml_property_type",type );


                xmlMapList.add(xmlMap);

            }*/
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从一个文件读入到String
     * @param FilePath
     * @return
     */
    public static String ReadFileToString(String FilePath)
    {
        FileInputStream fis = null;
        BufferedReader br = null;
        try
        {
            fis = new FileInputStream(FilePath);
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


    public static String code(InputStream fis)
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
                String[] split = temp.split("=");
                if(split!=null && split.length==2){

                    String s =split[0];
                    String s1 = split[1];

                    System.out.printf("wareSlaveData.set%s(appDashboardAnalysis.get%s());\n",
                            s.substring(0, 1).toUpperCase() + s.substring(1),
                            s1.substring(0, 1).toUpperCase() + s1.substring(1)
                    );
                }

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
