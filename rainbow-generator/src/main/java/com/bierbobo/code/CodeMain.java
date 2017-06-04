package com.bierbobo.code;

import java.io.IOException;
import java.util.List;

/**
 * Created by lifubo on 2017/6/3.
 */
public class CodeMain {

    public static void main(String[] args)  {


        System.out.println("1111111111111111");


    }



    ExcelServicePoiImpl excelServicePoi=new ExcelServicePoiImpl();
    GenerateServiceImpl generateService=new GenerateServiceImpl();

    public void run()throws Exception{


        String excelFilePath ="";
        String projectName = "";
        String javaProjectPath = "";
        
        String apiSubProjectName = projectName + "-api";
        String mainSubProjectName = projectName + "-main";
        String supportSubProjectName = projectName + "-support";

        System.out.println( "正在解析excel文件......");
        List entityMetaList = excelServicePoi.parseExcel(excelFilePath);
        System.out.println( "解析excel文件成功");

        generateAPI(javaProjectPath, apiSubProjectName, entityMetaList);
        
    }

    private void generateAPI(  String javaProjectPath,String apiSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        String mainJava = this.getSrcPath(javaProjectPath, apiSubProjectName, "src/main/java");
        String mainResources = this.getSrcPath(javaProjectPath, apiSubProjectName, "src/main/resources");
        System.out.println("开始生成API代码");

        for (EntityMeta entityMeta : entityMetaList)
        {
            System.out.println( "正在生成实体类......");
            generateService.generateDomainClass(mainJava, entityMeta);
 
            System.out.println("生成实体类成功");

            System.out.println("正在生查询条件类......");
            this.generateService.generateConditionClass(mainJava, entityMeta);

            System.out.println("生成查询条件类成功");

            System.out.println("正在生成Service接口......");
            this.generateService.generateServiceInterfaceClass(mainJava, entityMeta);

            System.out.println("生成Service接口成功");
        }

        this.generateService.generateServiceI18n(mainResources, entityMetaList);
    }

    private String getSrcPath(String javaProjectPath, String subProjectName, String srcPath)
    {
 

        return javaProjectPath+subProjectName+srcPath;
    }
    
}
