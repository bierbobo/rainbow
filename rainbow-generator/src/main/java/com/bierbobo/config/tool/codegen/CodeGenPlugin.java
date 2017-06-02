package com.bierbobo.config.tool.codegen;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class CodeGenPlugin extends AbstractUIPlugin
{
  private static CodeGenPlugin plugin;

  public CodeGenPlugin()
  {
    plugin = this;
  }

  public static CodeGenPlugin getDefault() {
    return plugin;
  }

  public void start(BundleContext context) throws Exception
  {
    super.start(context);
  }
}