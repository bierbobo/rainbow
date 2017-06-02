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

public class GenerateAllAction
  implements IObjectActionDelegate
{
  private static final Log log = LogFactory.getLog(GenerateAllAction.class);
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
          IJavaProject javaProject = JavaCore.create(GenerateAllAction.this.project);
          String projectName = GenerateAllAction.this.project.getName();

          String apiSubProjectName = projectName + "-api";
          String mainSubProjectName = projectName + "-main";
          String supportSubProjectName = projectName + "-support";

          ConsoleFactory.println("----------------------------------------------------------");
          GenerateAllAction.this.changeProgress(monitor, "正在解析excel文件......");
          List entityMetaList = GenerateAllAction.this.excelService.parseExcel(this.val$excelFilePath);
          ConsoleFactory.println("解析excel文件成功");

          generateAPI(monitor, javaProject, apiSubProjectName, entityMetaList);
          refreshSubProject(apiSubProjectName);
          generateSupport(monitor, javaProject, supportSubProjectName, entityMetaList);
          refreshSubProject(supportSubProjectName);
          generateMain(monitor, javaProject, mainSubProjectName, entityMetaList);
          refreshSubProject(mainSubProjectName);

          GenerateAllAction.this.project.refreshLocal(2, null);
          Thread.sleep(260L);
          monitor.done();
        } catch (Exception e) {
          ConsoleFactory.printStackTrace(e);
          MessageDialog.openError(GenerateAllAction.this.shell, "错误", "文件生成错误" + 
            e.getMessage());
        }
      }

      private void refreshSubProject(String apiSubProjectName)
        throws CoreException
      {
        IProject projectByName = GenerateAllAction.this.getProjectByName(apiSubProjectName);
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

        String mainJava = GenerateAllAction.this.getSrcPath(javaProject, supportSubProjectName, "src/main/java");
        String mainResources = GenerateAllAction.this.getSrcPath(javaProject, supportSubProjectName, "src/main/resources");
        GenerateAllAction.this.getSrcPath(javaProject, supportSubProjectName, "src/test/java");
        String testResources = GenerateAllAction.this.getSrcPath(javaProject, supportSubProjectName, "src/test/resources");
        for (EntityMeta entityMeta : entityMetaList) {
          GenerateAllAction.this.changeProgress(monitor, "正在生成myBatis实体关系映射文件......");
          GenerateAllAction.this.generateService.generateMapperFile(mainResources, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成myBatis实体关系映射文件成功");
          GenerateAllAction.this.changeProgress(monitor, "正在生成Dao接口......");
          GenerateAllAction.this.generateService.generateDaoInterfaceClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Dao接口成功");
          GenerateAllAction.this.changeProgress(monitor, "正在生成Manager接口......");
          GenerateAllAction.this.generateService.generateManagerInterfaceClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Manager接口成功");
          GenerateAllAction.this.changeProgress(monitor, "正在生成Manager实现类......");
          GenerateAllAction.this.generateService.generateManagerImplClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Manager实现类成功");
          GenerateAllAction.this.changeProgress(monitor, "正在生成Service实现类......");
          GenerateAllAction.this.generateService.generateServiceImplClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Service实现类成功");
        }

        GenerateAllAction.this.changeProgress(monitor, "正在生成建库脚本......");
        GenerateAllAction.this.generateService.generateDdl(testResources, entityMetaList);
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

        String mainJava = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/java");
        String mainResources = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/resources");
        String mainBin = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/bin");
        String mainAssemble = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/main/assemble");
        String testJava = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/test/java");
        String testResources = GenerateAllAction.this.getSrcPath(javaProject, mainSubProjectName, "src/test/resources");

        GenerateAllAction.this.changeProgress(monitor, "正在生成Launcher类......");
        GenerateAllAction.this.generateService.generateLauncher(mainJava, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成Launcher类成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成dao层spring配置文件......");
        GenerateAllAction.this.generateService.generateSpringDao(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成dao层spring配置文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成mybatis层spring配置文件......");
        GenerateAllAction.this.generateService.generateMybatisConfig(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成mybatis层spring配置文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成spring主配置文件......");
        GenerateAllAction.this.generateService.generateSpringMain(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成spring主配置文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成spring发布服务配置文件......");
        GenerateAllAction.this.generateService.generateSpringPublish(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成spring发布服务配置文件成功");

        ConsoleFactory.println("生成服务spring-ref配置文件");
        GenerateAllAction.this.generateService.generateSpringRef(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }

        ConsoleFactory.println("生成服务spring-ref配置文件成功!");

        ConsoleFactory.println("正在生成log4j属性文件...");
        GenerateAllAction.this.generateService.generateLog4jConfig(mainResources, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成log4j属性文件成功");

        ConsoleFactory.println("正在生成spring发布UMP服务配置文件...");
        GenerateAllAction.this.generateService.generateSpringUmp(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成spring发布UMP服务配置文件成功");

        ConsoleFactory.println("正在spring发布UMP服务配置文件...");
        GenerateAllAction.this.generateService.generateProfilerUmp(mainResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成spring发布Profiler.UMP服务配置文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在复制important属性文件......");
        GenerateAllAction.this.copyService.copy("properties", mainResources, "", "important.properties");
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("复制important属性文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在复制jdbc属性文件......");
        GenerateAllAction.this.copyService.copy("properties", mainResources, "prop", "jdbc.properties");
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("复制jdbc属性文件成功");

        GenerateAllAction.this.changeProgress(monitor, "正在复制application属性文件......");
        GenerateAllAction.this.copyService.copy("properties", mainResources, "prop", "application.properties");
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("复制application属性文件成功");

        for (EntityMeta entityMeta : entityMetaList) {
          GenerateAllAction.this.changeProgress(monitor, "正在生成Manager的junit测试类......");
          GenerateAllAction.this.generateService.generateManagerTestClass(testJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Manager的junit测试类成功");
        }

        GenerateAllAction.this.changeProgress(monitor, "正在复制package.xml......");
        GenerateAllAction.this.copyService.copy("assemble", mainAssemble, "", "package.xml");
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("复制package.xml成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成启动脚本......");
        GenerateAllAction.this.generateService.generateStartShCript(mainBin, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成启动脚本成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成停止脚本......");
        GenerateAllAction.this.generateService.generateStopShCript(mainBin, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成停止脚本成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成服务测试类......");
        GenerateAllAction.this.generateService.generateServiceTest(testJava, (EntityMeta)entityMetaList.get(0));
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成服务测试类成功");

        GenerateAllAction.this.changeProgress(monitor, "正在生成服务测试spring配置文件......");
        GenerateAllAction.this.generateService.generateSpringTestRef(testResources, entityMetaList);
        if (monitor.isCanceled()) {
          return;
        }
        ConsoleFactory.println("生成服务测试spring配置文件成功");
      }

      private void generateAPI(IProgressMonitor monitor, IJavaProject javaProject, String apiSubProjectName, List<EntityMeta> entityMetaList)
        throws InterruptedException, IOException
      {
        String mainJava = GenerateAllAction.this.getSrcPath(javaProject, apiSubProjectName, "src/main/java");
        String mainResources = GenerateAllAction.this.getSrcPath(javaProject, apiSubProjectName, "src/main/resources");
        ConsoleFactory.println("");
        ConsoleFactory.println("----------------------------------------------------------");
        ConsoleFactory.println("开始生成API代码");

        for (EntityMeta entityMeta : entityMetaList)
        {
          GenerateAllAction.this.changeProgress(monitor, "正在生成实体类......");
          GenerateAllAction.this.generateService.generateDomainClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成实体类成功");

          GenerateAllAction.this.changeProgress(monitor, "正在生查询条件类......");
          GenerateAllAction.this.generateService.generateConditionClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成查询条件类成功");

          GenerateAllAction.this.changeProgress(monitor, "正在生成Service接口......");
          GenerateAllAction.this.generateService.generateServiceInterfaceClass(mainJava, entityMeta);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成Service接口成功");
        }

        GenerateAllAction.this.generateService.generateServiceI18n(mainResources, entityMetaList);
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