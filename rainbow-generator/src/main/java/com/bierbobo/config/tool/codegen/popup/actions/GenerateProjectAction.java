package com.bierbobo.config.tool.codegen.popup.actions;

import com.bierbobo.config.tool.codegen.service.CopyService;
import com.bierbobo.config.tool.codegen.service.GenerateService;
import com.bierbobo.config.tool.codegen.service.impl.CopyServiceImpl;
import com.bierbobo.config.tool.codegen.service.impl.ExcelServicePoiImpl;
import com.bierbobo.config.tool.codegen.console.ConsoleFactory;
import com.bierbobo.config.tool.codegen.domain.EntityMeta;
import com.bierbobo.config.tool.codegen.service.ExcelService;
import com.bierbobo.config.tool.codegen.service.impl.GenerateServiceImpl;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

public class GenerateProjectAction
  implements IObjectActionDelegate
{
  private static final Log log = LogFactory.getLog(GenerateProjectAction.class);
  private Shell shell;
  private IProject project;
  private ExcelService excelService = new ExcelServicePoiImpl();

  private GenerateService generateService = new GenerateServiceImpl();

  private CopyService copyService = new CopyServiceImpl();

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
    this.shell = targetPart.getSite().getShell();
  }

  public void run(IAction action)
  {
    FileDialog dialog = new FileDialog(this.shell, 4096);
    dialog.setText("选择文件");

    String excelFilePath = dialog.open();
    if (excelFilePath == null) {
      return;
    }
    IRunnableWithProgress runnable = new IRunnableWithProgress(excelFilePath) {
      public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        monitor.beginTask("代码生成中，请稍候！", 20);
        try
        {
          IJavaProject javaProject = JavaCore.create(GenerateProjectAction.this.project);
          String projectName = GenerateProjectAction.this.project.getName();

          String apiSubProjectName = projectName + "-api";
          String mainSubProjectName = projectName + "-main";
          String supportSubProjectName = projectName + "-support";
          String webappSubProjectName = projectName + "-webapp";

          ConsoleFactory.println("----------------------------------------------------------");
          GenerateProjectAction.this.changeProgress(monitor, "正在解析excel文件......");
          List entityMetaList = GenerateProjectAction.this.excelService.parseExcel(this.val$excelFilePath);
          ConsoleFactory.println("解析excel文件成功");

          generateAPI(monitor, javaProject, apiSubProjectName, entityMetaList);
          refreshSubProject(apiSubProjectName);
          generateSupport(monitor, javaProject, supportSubProjectName, entityMetaList);
          refreshSubProject(supportSubProjectName);
          generateMain(monitor, javaProject, mainSubProjectName, entityMetaList);
          refreshSubProject(mainSubProjectName);

          GenerateProjectAction.this.generateWebapp(monitor, javaProject, webappSubProjectName, entityMetaList);
          refreshSubProject(mainSubProjectName);

          GenerateProjectAction.this.project.refreshLocal(2, null);
          Thread.sleep(260L);
          monitor.done();
        } catch (Exception e) {
          ConsoleFactory.printStackTrace(e);
          MessageDialog.openError(GenerateProjectAction.this.shell, "错误", "文件生成错误" + 
            e.getMessage());
        }
      }

      private void refreshSubProject(String apiSubProjectName)
        throws CoreException
      {
        IProject projectByName = GenerateProjectAction.this.getProjectByName(apiSubProjectName);
        if (projectByName != null)
          projectByName.refreshLocal(2, null);
        else
          ConsoleFactory.println(String.format("Error：项目[%s]在工作空间中不存在！", new Object[] { projectByName }));
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
    };
    try {
      new ProgressMonitorDialog(this.shell).run(true, true, runnable);
    } catch (InvocationTargetException e) {
      ConsoleFactory.printStackTrace(e);
    } catch (InterruptedException e) {
      ConsoleFactory.printStackTrace(e);
    }
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

  private void changeProgress(IProgressMonitor monitor, String taskName)
    throws InterruptedException
  {
    monitor.setTaskName(taskName);
    Thread.sleep(60L);
    monitor.worked(1);
  }

  private IProject getProjectByName(String projectName) {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    IProject project = workspace.getRoot().getProject(projectName);
    return project;
  }

  private String getSrcPath(IJavaProject javaProject, String subProjectName, String srcPath)
  {
    IPackageFragmentRoot srcPathElement = javaProject.getPackageFragmentRoot(srcPath);
    IPath location = javaProject.getProject().getLocation();
    IPath append = location.append(subProjectName);
    append = append.append(srcPath);
    if (!srcPathElement.exists()) {
      JavaCore.newSourceEntry(append);
    }
    String srcFolder = append.toOSString();

    return srcFolder;
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    IStructuredSelection ss = (IStructuredSelection)selection;
    Object firstElement = ss.getFirstElement();
    if ((firstElement instanceof IProject))
      this.project = ((IProject)firstElement);
  }
}