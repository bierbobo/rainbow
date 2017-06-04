package com.bierbobo.rainbow.coder.code;

import java.io.IOException;
import java.util.List;

/**
 * Created by lifubo on 2017/6/3.
 */
public class CodeMain {

    public static void main(String[] args)  throws Exception{


        System.out.println("1111111111111111");
        CodeMain codeMain=new CodeMain();
        codeMain.run();

    }



    ExcelServicePoiImpl excelServicePoi=new ExcelServicePoiImpl();
    GenerateServiceImpl generateService=new GenerateServiceImpl();
    CopyServiceImpl copyService=new CopyServiceImpl();

    public void run()throws Exception{


        String excelFilePath ="c:/code/logistics.xlsx";
        String projectName = "/abc";
        String javaProjectPath = "c:/code";
        
        String apiSubProjectName = projectName + "-api";
        String mainSubProjectName = projectName + "-main";
        String supportSubProjectName = projectName + "-support";

        System.out.println( "正在解析excel文件......");
        List entityMetaList = excelServicePoi.parseExcel(excelFilePath);
        System.out.println( "解析excel文件成功");

        generateAPI(javaProjectPath, apiSubProjectName, entityMetaList);
        generateSupport(javaProjectPath, mainSubProjectName, entityMetaList);
        generateMain(javaProjectPath, supportSubProjectName, entityMetaList);


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

//        this.generateService.generateServiceI18n(mainResources, entityMetaList);
    }

    private void generateSupport(String javaProjectPath, String supportSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("开始生成实现代码");

        String mainJava = getSrcPath(javaProjectPath, supportSubProjectName, "src/main/java");
        String mainResources = getSrcPath(javaProjectPath, supportSubProjectName, "src/main/resources");
//        getSrcPath(javaProjectPath, supportSubProjectName, "src/test/java");
        String testResources = getSrcPath(javaProjectPath, supportSubProjectName, "src/test/resources");
        for (EntityMeta entityMeta : entityMetaList) {
           System.out.println("正在生成myBatis实体关系映射文件......");
            generateService.generateMapperFile(mainResources, entityMeta);

            System.out.println("生成myBatis实体关系映射文件成功");
           System.out.println("正在生成Dao接口......");
            generateService.generateDaoInterfaceClass(mainJava, entityMeta);

            System.out.println("生成Dao接口成功");
           System.out.println("正在生成Manager接口......");
            generateService.generateManagerInterfaceClass(mainJava, entityMeta);

            System.out.println("生成Manager接口成功");
           System.out.println("正在生成Manager实现类......");
            generateService.generateManagerImplClass(mainJava, entityMeta);

            System.out.println("生成Manager实现类成功");
           System.out.println("正在生成Service实现类......");
            generateService.generateServiceImplClass(mainJava, entityMeta);

            System.out.println("生成Service实现类成功");
        }

       System.out.println("正在生成建库脚本......");
        generateService.generateDdl(testResources, entityMetaList);

        System.out.println("生成建库脚本成功");
    }



    private void generateMain(String javaProjectPath, String mainSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        System.out.println("代码生成中，请稍候！");
        System.out.println("");
        System.out.println("----------------------------------------------------------");
        System.out.println("开始生成Main代码");

        String mainJava = getSrcPath(javaProjectPath, mainSubProjectName, "src/main/java");
        String mainResources = getSrcPath(javaProjectPath, mainSubProjectName, "src/main/resources");
        String mainBin = getSrcPath(javaProjectPath, mainSubProjectName, "src/main/bin");
        String mainAssemble = getSrcPath(javaProjectPath, mainSubProjectName, "src/main/assemble");
        String testJava = getSrcPath(javaProjectPath, mainSubProjectName, "src/test/java");
        String testResources = getSrcPath(javaProjectPath, mainSubProjectName, "src/test/resources");

       System.out.println("正在生成Launcher类......");
        generateService.generateLauncher(mainJava, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成Launcher类成功");

       System.out.println("正在生成dao层spring配置文件......");
        generateService.generateSpringDao(mainResources, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成dao层spring配置文件成功");

       System.out.println("正在生成mybatis层spring配置文件......");
        generateService.generateMybatisConfig(mainResources, entityMetaList);

        System.out.println("生成mybatis层spring配置文件成功");

       System.out.println("正在生成spring主配置文件......");
        generateService.generateSpringMain(mainResources, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成spring主配置文件成功");

       System.out.println("正在生成spring发布服务配置文件......");
        generateService.generateSpringPublish(mainResources, entityMetaList);

        System.out.println("生成spring发布服务配置文件成功");

        System.out.println("生成服务spring-ref配置文件");
        generateService.generateSpringRef(mainResources, entityMetaList);


        System.out.println("生成服务spring-ref配置文件成功!");

        System.out.println("正在生成log4j属性文件...");
        generateService.generateLog4jConfig(mainResources, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成log4j属性文件成功");

        System.out.println("正在生成spring发布UMP服务配置文件...");
        generateService.generateSpringUmp(mainResources, entityMetaList);

        System.out.println("生成spring发布UMP服务配置文件成功");

        System.out.println("正在spring发布UMP服务配置文件...");
        generateService.generateProfilerUmp(mainResources, entityMetaList);

        System.out.println("生成spring发布Profiler.UMP服务配置文件成功");

       System.out.println("正在复制important属性文件......");
        copyService.copy("properties", mainResources, "", "important.properties");

        System.out.println("复制important属性文件成功");

       System.out.println("正在复制jdbc属性文件......");
        copyService.copy("properties", mainResources, "prop", "jdbc.properties");
        System.out.println("复制jdbc属性文件成功");

       System.out.println("正在复制application属性文件......");
        copyService.copy("properties", mainResources, "prop", "application.properties");
        System.out.println("复制application属性文件成功");

        for (EntityMeta entityMeta : entityMetaList) {
           System.out.println("正在生成Manager的junit测试类......");
            generateService.generateManagerTestClass(testJava, entityMeta);
            System.out.println("生成Manager的junit测试类成功");
        }

       System.out.println("正在复制package.xml......");
        copyService.copy("assemble", mainAssemble, "", "package.xml");
        System.out.println("复制package.xml成功");

       System.out.println("正在生成启动脚本......");
        generateService.generateStartShCript(mainBin, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成启动脚本成功");

       System.out.println("正在生成停止脚本......");
        generateService.generateStopShCript(mainBin, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成停止脚本成功");

       System.out.println("正在生成服务测试类......");
        generateService.generateServiceTest(testJava, (EntityMeta)entityMetaList.get(0));

        System.out.println("生成服务测试类成功");

       System.out.println("正在生成服务测试spring配置文件......");
        generateService.generateSpringTestRef(testResources, entityMetaList);

        System.out.println("生成服务测试spring配置文件成功");
    }
    
    private String getSrcPath(String javaProjectPath, String subProjectName, String srcPath)
    {
 

        return javaProjectPath+subProjectName+srcPath;
    }
    
}
