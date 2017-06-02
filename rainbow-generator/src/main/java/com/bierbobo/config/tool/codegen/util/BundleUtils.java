package com.bierbobo.config.tool.codegen.util;

import java.net.URL;
import java.util.Enumeration;
import org.osgi.framework.Bundle;

public class BundleUtils
{
  public static ClassLoader getBundleClassLoader(Bundle bundle)
  {
    Enumeration classFileEntries = bundle.findEntries("/", "*.class", 
      true);
    if ((classFileEntries == null) || (!classFileEntries.hasMoreElements())) {
      throw new RuntimeException(String.format("Bundle[%s]中没有一个Java类！", new Object[] { 
        bundle.getSymbolicName() }));
    }

    URL url = (URL)classFileEntries.nextElement();

    String bundleOneClassName = url.getPath();

    bundleOneClassName = bundleOneClassName.replace("/", ".").substring(0, 
      bundleOneClassName.lastIndexOf("."));

    while (bundleOneClassName.startsWith(".")) {
      bundleOneClassName = bundleOneClassName.substring(1);
    }
    Class bundleOneClass = null;
    try
    {
      bundleOneClass = bundle.loadClass(bundleOneClassName);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    return bundleOneClass.getClassLoader();
  }
}