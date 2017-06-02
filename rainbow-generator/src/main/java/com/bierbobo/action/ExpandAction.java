package com.bierbobo.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ExpandAction
  implements IObjectActionDelegate
{
  private static final Log log = LogFactory.getLog(ExpandAction.class);
  private Shell shell;
  private IProject project;
  private TreeViewer treeViewer;

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
    this.shell = targetPart.getSite().getShell();
  }

  public void run(IAction action)
  {
    IJavaProject javaProject = JavaCore.create(this.project);
    try
    {
      IJavaElement[] children = javaProject.getChildren();
      for (IJavaElement child : children)
        expand(child);
    }
    catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private void expand(IJavaElement javaElement)
    throws CoreException
  {
    if (javaElement != null)
    {
      Object localObject3;
      Object object;
      if ((javaElement instanceof IPackageFragmentRoot)) {
        IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot)javaElement;
        if (packageFragmentRoot.getKind() == 1) {
          getTreeViewer().reveal(packageFragmentRoot);

          IJavaElement[] children = packageFragmentRoot.getChildren();
          IJavaElement[] arrayOfIJavaElement1;
          localObject3 = (arrayOfIJavaElement1 = children).length; for (Object localObject1 = 0; localObject1 < localObject3; localObject1++) { Object child = arrayOfIJavaElement1[localObject1];
            if ((child instanceof IJavaElement)) {
              expand((IJavaElement)child);
            }
          }

          Object[] nonJavaResources = packageFragmentRoot.getNonJavaResources();
          Object[] arrayOfObject1;
          int i = (arrayOfObject1 = nonJavaResources).length; for (localObject3 = 0; localObject3 < i; localObject3++) { object = arrayOfObject1[localObject3];
            if ((object instanceof IFolder)) {
              IFolder folder = (IFolder)object;
              expandNonJavaResource(folder);
            } else {
              getTreeViewer().reveal(object);
            }
          }
        }
      }
      else if ((javaElement instanceof IPackageFragment)) {
        IPackageFragment packageFragment = (IPackageFragment)javaElement;
        ICompilationUnit[] compilationUnits = packageFragment.getCompilationUnits();
        ICompilationUnit[] arrayOfICompilationUnit1;
        localObject3 = (arrayOfICompilationUnit1 = compilationUnits).length; for (Object localObject2 = 0; localObject2 < localObject3; localObject2++) { ICompilationUnit compilationUnit = arrayOfICompilationUnit1[localObject2];
          expand(compilationUnit); }
      }
      else {
        getTreeViewer().reveal(javaElement);
      }
    }
  }

  private void expandNonJavaResource(IFolder folder)
    throws CoreException
  {
    IResource[] members = folder.members();
    for (IResource resource : members)
      if ((resource instanceof IFolder))
        expandNonJavaResource(folder);
      else if ((resource instanceof IFile))
        getTreeViewer().reveal(resource);
  }

  private TreeViewer getTreeViewer()
  {
    if (this.treeViewer == null) {
      this.treeViewer = getPackageExplorerPart().getTreeViewer();
    }

    return this.treeViewer;
  }

  private PackageExplorerPart getPackageExplorerPart()
  {
    IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    PackageExplorerPart packageExplorerPart = (PackageExplorerPart)activePage.findView("org.eclipse.jdt.ui.PackageExplorer");
    return packageExplorerPart;
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    IStructuredSelection ss = (IStructuredSelection)selection;
    Object firstElement = ss.getFirstElement();
    if ((firstElement instanceof IProject))
      this.project = ((IProject)firstElement);
  }
}