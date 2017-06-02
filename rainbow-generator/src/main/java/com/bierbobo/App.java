package com.bierbobo;

import com.bierbobo.service.ExcelService;
import com.bierbobo.service.impl.ExcelServicePoiImpl;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );




    }

    private ExcelService excelService = new ExcelServicePoiImpl();
    public void run() throws IOException {

        String projectName = "";

        String apiSubProjectName = projectName + "-api";
        String mainSubProjectName = projectName + "-main";
        String supportSubProjectName = projectName + "-support";
        String webappSubProjectName = projectName + "-webapp";

        List entityMetaList = this.excelService.parseExcel("");

        generateAPI(monitor, javaProject, apiSubProjectName, entityMetaList);
        generateSupport(monitor, javaProject, supportSubProjectName, entityMetaList);
        generateMain(monitor, javaProject, mainSubProjectName, entityMetaList);
        generateWebapp(monitor, javaProject, webappSubProjectName, entityMetaList);

    }


    private void generateAPI(IProgressMonitor monitor, IJavaProject javaProject, String apiSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        String mainJava = GenerateProjectAction.this.getSrcPath(javaProject, apiSubProjectName, "src/main/java");
        String mainResources = GenerateProjectAction.this.getSrcPath(javaProject, apiSubProjectName, "src/main/resources");
        ConsoleFactory.println("");
        ConsoleFactory.println("----------------------------------------------------------");
        ConsoleFactory.println("开始生成API代码");

        for (EntityMeta entityMeta : entityMetaList)
        {
            GenerateProjectAction.this.changeProgress(monitor, "正在生成实体类......");
            GenerateProjectAction.this.generateService.generateDomainClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成实体类成功");

            GenerateProjectAction.this.changeProgress(monitor, "正在生查询条件类......");
            GenerateProjectAction.this.generateService.generateConditionClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成查询条件类成功");

            GenerateProjectAction.this.changeProgress(monitor, "正在生成Service接口......");
            GenerateProjectAction.this.generateService.generateServiceInterfaceClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Service接口成功");
        }

        GenerateProjectAction.this.generateService.generateServiceI18n(mainResources, entityMetaList);
    }

    private void generateSupport(IProgressMonitor monitor, IJavaProject javaProject, String supportSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        ConsoleFactory.println("");
        ConsoleFactory.println("----------------------------------------------------------");
        ConsoleFactory.println("开始生成实现代码");

        String mainJava = GenerateProjectAction.this.getSrcPath(javaProject, supportSubProjectName, "src/main/java");
        String mainResources = GenerateProjectAction.this.getSrcPath(javaProject, supportSubProjectName, "src/main/resources");
        GenerateProjectAction.this.getSrcPath(javaProject, supportSubProjectName, "src/test/java");
        String testResources = GenerateProjectAction.this.getSrcPath(javaProject, supportSubProjectName, "src/test/resources");
        for (EntityMeta entityMeta : entityMetaList) {
            GenerateProjectAction.this.changeProgress(monitor, "正在生成myBatis实体关系映射文件......");
            GenerateProjectAction.this.generateService.generateMapperFile(mainResources, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成myBatis实体关系映射文件成功");
            GenerateProjectAction.this.changeProgress(monitor, "正在生成Dao接口......");
            GenerateProjectAction.this.generateService.generateDaoInterfaceClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Dao接口成功");
            GenerateProjectAction.this.changeProgress(monitor, "正在生成Manager接口......");
            GenerateProjectAction.this.generateService.generateManagerInterfaceClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Manager接口成功");
            GenerateProjectAction.this.changeProgress(monitor, "正在生成Manager实现类......");
            GenerateProjectAction.this.generateService.generateManagerImplClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Manager实现类成功");
            GenerateProjectAction.this.changeProgress(monitor, "正在生成Service实现类......");
            GenerateProjectAction.this.generateService.generateServiceImplClass(mainJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Service实现类成功");
        }

        GenerateProjectAction.this.changeProgress(monitor, "正在生成建库脚本......");
        GenerateProjectAction.this.generateService.generateDdl(testResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成建库脚本成功");
    }

    private void generateMain(IProgressMonitor monitor, IJavaProject javaProject, String mainSubProjectName, List<EntityMeta> entityMetaList)
            throws InterruptedException, IOException
    {
        monitor.beginTask("代码生成中，请稍候！", 20);
        ConsoleFactory.println("");
        ConsoleFactory.println("----------------------------------------------------------");
        ConsoleFactory.println("开始生成Main代码");

        String mainJava = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/java");
        String mainResources = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/resources");
        String mainBin = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/bin");
        String mainAssemble = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/assemble");
        String testJava = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/test/java");
        String testResources = GenerateProjectAction.this.getSrcPath(javaProject, mainSubProjectName, "src/test/resources");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成Launcher类......");
        GenerateProjectAction.this.generateService.generateLauncher(mainJava, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成Launcher类成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成dao层spring配置文件......");
        GenerateProjectAction.this.generateService.generateSpringDao(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成dao层spring配置文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成mybatis层spring配置文件......");
        GenerateProjectAction.this.generateService.generateMybatisConfig(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成mybatis层spring配置文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成spring主配置文件......");
        GenerateProjectAction.this.generateService.generateSpringMain(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成spring主配置文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成spring发布服务配置文件......");
        GenerateProjectAction.this.generateService.generateSpringPublish(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成spring发布服务配置文件成功");

        ConsoleFactory.println("生成服务spring-ref配置文件");
        GenerateProjectAction.this.generateService.generateSpringRef(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }

        ConsoleFactory.println("生成服务spring-ref配置文件成功!");

        ConsoleFactory.println("正在生成log4j属性文件...");
        GenerateProjectAction.this.generateService.generateLog4jConfig(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成log4j属性文件成功");

        ConsoleFactory.println("正在生成spring发布UMP服务配置文件...");
        GenerateProjectAction.this.generateService.generateSpringUmp(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成spring发布UMP服务配置文件成功");

        ConsoleFactory.println("正在spring发布UMP服务配置文件...");
        GenerateProjectAction.this.generateService.generateProfilerUmp(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成spring发布Profiler.UMP服务配置文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在复制important属性文件......");
        GenerateProjectAction.this.copyService.copy("properties", mainResources, "", "important.properties");
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("复制important属性文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在复制jdbc属性文件......");
        GenerateProjectAction.this.copyService.copy("properties", mainResources, "prop", "jdbc.properties");
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("复制jdbc属性文件成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在复制application属性文件......");
        GenerateProjectAction.this.copyService.copy("properties", mainResources, "prop", "application.properties");
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("复制application属性文件成功");

        for (EntityMeta entityMeta : entityMetaList) {
            GenerateProjectAction.this.changeProgress(monitor, "正在生成Manager的junit测试类......");
            GenerateProjectAction.this.generateService.generateManagerTestClass(testJava, entityMeta);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成Manager的junit测试类成功");
        }

        GenerateProjectAction.this.changeProgress(monitor, "正在复制package.xml......");
        GenerateProjectAction.this.copyService.copy("assemble", mainAssemble, "", "package.xml");
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("复制package.xml成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成启动脚本......");
        GenerateProjectAction.this.generateService.generateStartShCript(mainBin, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成启动脚本成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成停止脚本......");
        GenerateProjectAction.this.generateService.generateStopShCript(mainBin, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成停止脚本成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成服务测试类......");
        GenerateProjectAction.this.generateService.generateServiceTest(testJava, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成服务测试类成功");

        GenerateProjectAction.this.changeProgress(monitor, "正在生成服务测试spring配置文件......");
        GenerateProjectAction.this.generateService.generateSpringTestRef(testResources, entityMetaList);
        if (monitor.isCanceled()) {
            return;
        }
        ConsoleFactory.println("生成服务测试spring配置文件成功");
    }

    protected void generateWebapp(IProgressMonitor monitor, IJavaProject javaProject, String webappSubProjectName, List<EntityMeta> entityMetaList)
    {
        monitor.beginTask("代码生成中，请稍候！", 20);
        try {
            String mainJava = getSrcPath(javaProject, webappSubProjectName, "src/main/java");
            String mainResources = getSrcPath(javaProject, webappSubProjectName, "src/main/resources");
            String mainWebapp = getSrcPath(javaProject, webappSubProjectName, "src/main/webapp");

            changeProgress(monitor, "正在解析excel文件......");
            for (EntityMeta entityMeta : entityMetaList)
            {
                changeProgress(monitor, "正在生成Controller......");
                this.generateService.generateController(mainJava, entityMeta);
                if (monitor.isCanceled()) {
                    return;
                }
                ConsoleFactory.println("生成Controller成功");

                changeProgress(monitor, "正在生成spring-web.xml......");
                this.generateService.generateSpringWeb(mainResources, entityMetaList);
                if (monitor.isCanceled()) {
                    return;
                }
                ConsoleFactory.println("生成spring-web.xml成功");

                changeProgress(monitor, "正在生成VM模板......");
                this.generateService.generateVelocityPage(mainWebapp, entityMeta);
                if (monitor.isCanceled()) {
                    return;
                }
                ConsoleFactory.println("生成VM模板成功");

                changeProgress(monitor, "正在生成JS文件......");
                this.generateService.generateJavaScript(mainWebapp, entityMeta);
                if (monitor.isCanceled()) {
                    return;
                }
                ConsoleFactory.println("生成JS文件成功");
            }

            ConsoleFactory.println("开始生成main.vm文件成...");
            this.generateService.generateVelocityDefaultPage(mainWebapp, (EntityMeta)entityMetaList.get(0));
            ConsoleFactory.println("生成main.vm文件成功");

            ConsoleFactory.println("开始生成生成VM模板");
            this.generateService.generateJavaScriptCommonFn(mainWebapp, (EntityMeta)entityMetaList.get(0));
            System.out.println("生成VM模板成功");
            ConsoleFactory.println("生成VM模板成功");

            changeProgress(monitor, "正在生成ExcelConstant......");
            this.generateService.generateExcelConstant(mainJava, entityMetaList);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成ExcelConstant文件成功");

            changeProgress(monitor, "正在生成mainController......");
            this.generateService.generateMainController(mainJava, entityMetaList);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成mainController文件成功");

            ConsoleFactory.println("生成i18n国际化文件中.....");
            this.generateService.generateI18n(mainResources, entityMetaList);
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("生成i18n国际化文件中成功");

            changeProgress(monitor, "正在复制application属性文件......");
            this.copyService.copy("properties", mainResources, "prop", "application.properties");
            if (monitor.isCanceled()) {
                return;
            }
            ConsoleFactory.println("复制application属性文件成功");

            changeProgress(monitor, "正在生成服务测试spring配置文件......");

            this.generateService.generateSpringWEBMain(mainResources, entityMetaList);
            if (monitor.isCanceled()) {
                return;
            }

            ConsoleFactory.println("生成服务测试spring-Main配置文件");
            this.generateService.generateSpringWebRef(mainResources, entityMetaList);
            if (monitor.isCanceled()) {
                return;
            }

            ConsoleFactory.println("生成服务测试spring配置文件");

            this.project.refreshLocal(2, null);
            Thread.sleep(500L);
            monitor.done();
        } catch (Exception e) {
            ConsoleFactory.printStackTrace(e);
            MessageDialog.openError(this.shell, "错误", "文件生成错误" +
                    e.getMessage());
        }
    }


}
