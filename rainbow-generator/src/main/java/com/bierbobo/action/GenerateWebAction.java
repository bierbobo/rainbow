package com.bierbobo.action;


import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
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

public class GenerateWebAction
  implements IObjectActionDelegate
{
  private static final Log log = LogFactory.getLog(GenerateWebAction.class);
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
          IJavaProject javaProject = JavaCore.create(GenerateWebAction.this.project);
          String mainJava = GenerateWebAction.this.getSrcPath(javaProject, "src/main/java");
          String mainResources = GenerateWebAction.this.getSrcPath(javaProject, "src/main/resources");
          String mainWebapp = GenerateWebAction.this.getSrcPath(javaProject, "src/main/webapp");

          GenerateWebAction.this.changeProgress(monitor, "正在解析excel文件......");
          List entityMetaList = GenerateWebAction.this.excelService.parseExcel(this.val$excelFilePath);
          for (EntityMeta entityMeta : entityMetaList)
          {
            GenerateWebAction.this.changeProgress(monitor, "正在生成Controller......");
            GenerateWebAction.this.generateService.generateController(mainJava, entityMeta);
            if (monitor.isCanceled()) {
              return;
            }
            ConsoleFactory.println("生成Controller成功");

            GenerateWebAction.this.changeProgress(monitor, "正在生成spring-web.xml......");
            GenerateWebAction.this.generateService.generateSpringWeb(mainResources, entityMetaList);
            if (monitor.isCanceled()) {
              return;
            }
            ConsoleFactory.println("生成spring-web.xml成功");

            GenerateWebAction.this.changeProgress(monitor, "正在生成VM模板......");
            GenerateWebAction.this.generateService.generateVelocityPage(mainWebapp, entityMeta);
            if (monitor.isCanceled()) {
              return;
            }
            ConsoleFactory.println("生成VM模板成功");

            GenerateWebAction.this.changeProgress(monitor, "正在生成JS文件......");
            GenerateWebAction.this.generateService.generateJavaScript(mainWebapp, entityMeta);
            if (monitor.isCanceled()) {
              return;
            }
            ConsoleFactory.println("生成JS文件成功");
          }

          ConsoleFactory.println("开始生成main.vm文件成...");
          GenerateWebAction.this.generateService.generateVelocityDefaultPage(mainWebapp, (EntityMeta)entityMetaList.get(0));
          ConsoleFactory.println("生成main.vm文件成功");

          ConsoleFactory.println("开始生成生成VM模板");
          GenerateWebAction.this.generateService.generateJavaScriptCommonFn(mainWebapp, (EntityMeta)entityMetaList.get(0));
          System.out.println("生成VM模板成功");
          ConsoleFactory.println("生成VM模板成功");

          GenerateWebAction.this.changeProgress(monitor, "正在生成ExcelConstant......");
          GenerateWebAction.this.generateService.generateExcelConstant(mainJava, entityMetaList);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成ExcelConstant文件成功");

          GenerateWebAction.this.changeProgress(monitor, "正在生成mainController......");
          GenerateWebAction.this.generateService.generateMainController(mainJava, entityMetaList);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成mainController文件成功");

          ConsoleFactory.println("生成i18n国际化文件中.....");
          GenerateWebAction.this.generateService.generateI18n(mainResources, entityMetaList);
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("生成i18n国际化文件中成功");

          GenerateWebAction.this.changeProgress(monitor, "正在复制application属性文件......");
          GenerateWebAction.this.copyService.copy("properties", mainResources, "prop", "application.properties");
          if (monitor.isCanceled()) {
            return;
          }
          ConsoleFactory.println("复制application属性文件成功");

          GenerateWebAction.this.changeProgress(monitor, "正在生成服务测试spring配置文件......");

          GenerateWebAction.this.generateService.generateSpringWEBMain(mainResources, entityMetaList);
          if (monitor.isCanceled()) {
            return;
          }

          ConsoleFactory.println("生成服务测试spring-Main配置文件");
          GenerateWebAction.this.generateService.generateSpringWebRef(mainResources, entityMetaList);
          if (monitor.isCanceled()) {
            return;
          }

          ConsoleFactory.println("生成服务测试spring配置文件");

          GenerateWebAction.this.project.refreshLocal(2, null);
          Thread.sleep(500L);
          monitor.done();
        } catch (Exception e) {
          ConsoleFactory.printStackTrace(e);
          MessageDialog.openError(GenerateWebAction.this.shell, "错误", "文件生成错误" + 
            e.getMessage());
        }
      } } ;
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

  private String getSrcPath(IJavaProject javaProject, String srcPath)
  {
    IPackageFragmentRoot srcPathElement = javaProject.getPackageFragmentRoot(srcPath);
    IPath location = javaProject.getProject().getLocation();
    IPath append = location.append(srcPath);
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