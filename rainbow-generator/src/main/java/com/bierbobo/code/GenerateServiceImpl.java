package com.bierbobo.code;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


public class GenerateServiceImpl
{
  private static final Log log = LogFactory.getLog(GenerateServiceImpl.class);
  private static final String TIMESTAMP = "timestamp";
  private static final String TS = "ts";
  private static final String DATE = "Date";
  private static final String BYTE = "byte";
  private static final String INT = "int";
  private static final String STRING = "String";
  private static final String AUTOSEQID_SQL = "auto_Seq_id";
  private static final String AUTOSEQID_JAVA = "autoSeqId";
  private static final String BIGINT = "bigint";
  private static final String TINYINT = "tinyint";
  private static final String VARCHAR = "varchar";
  private static final String VERSION = "version";
  private static final String RESERVE1 = "reserve1";
  private static final String RESERVE2 = "reserve2";
  private static final String TEST = "test";

  public void generateDomainClass(String srcFolder,EntityMeta entityMeta)
          throws IOException
  {
    generateFile(entityMeta, srcFolder,
            "/domain", ".java", "java/Bean.java.vm");
  }

  public void generateConditionClass(String srcFolder, EntityMeta entityMeta) throws IOException {
    generateFile(entityMeta, srcFolder,
            "/condition", "Condition.java", "java/Condition.java.vm");
  }

  private void generateFile(EntityMeta entityMeta, String filePath, String filePathSuffix, String fileNameSuffix, String templatePath)
          throws IOException
  {
    generateFile(entityMeta, filePath, filePathSuffix, fileNameSuffix, templatePath, true);
  }

  public void generateServiceInterfaceClass(String srcFolder,EntityMeta entityMeta)
          throws IOException
  {
    generateFile(entityMeta, srcFolder,
            "/service", "Service.java", "java/ServiceInterface.java.vm");
  }


  public void generateServiceI18n(String srcFolder, List<EntityMeta> entityMetaList)
          throws IOException
  {
    Map fieldMetaMap = new HashMap();
    for (EntityMeta entityMeta : entityMetaList) {
      for (FieldMeta fieldMeta : entityMeta.getFieldMetaList()) {
        fieldMetaMap.put(fieldMeta.getJavaFieldName(), fieldMeta.getComment());
      }
    }
    if (((EntityMeta)entityMetaList.get(0)).getProjectMeta().isI18n()) {
      generatePropertiesConfigFile(entityMetaList, fieldMetaMap, srcFolder,
              "/i18n", ((EntityMeta)entityMetaList.get(0)).getProjectMeta().getModuleName() + "_en_US.properties", "i18n/i18n_module_en_US.properties.vm");
      generatePropertiesConfigFile(entityMetaList, fieldMetaMap, srcFolder,
              "/i18n", ((EntityMeta)entityMetaList.get(0)).getProjectMeta().getModuleName() + "_zh_CN.properties", "i18n/i18n_module_zh_CN.properties.vm");
    }
  }


  private void generatePropertiesConfigFile(List<EntityMeta> entityMetaList, Map<String, String> fieldMetaMap, String srcFolder, String packageSuffix, String fileName, String templatePath)
          throws IOException
  {
    FileOutputStream os = null;
    try {
      File dir = new File(srcFolder + packageSuffix);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      os = FileUtils.openOutputStream(new File(dir, fileName));

      Writer writer = new OutputStreamWriter(os, Charset.forName("UTF-8"));
      Map map = new HashMap();
      map.put("fieldMetaMap", fieldMetaMap);
      map.put("entityMetaList", entityMetaList);
      execute(templatePath, map, writer);
      writer.flush();
    } finally {
      IOUtils.closeQuietly(os);
    }
  }

  
  private void generateFile(EntityMeta entityMeta, String srcFolder, String packageSuffix, String fileNameSuffix, String templatePath, boolean isClass)
          throws IOException
  {
    FileOutputStream os = null;
    try {
      String basePackage = null;
      if (isClass)
        basePackage = "/" + entityMeta.getPackageName().replaceAll("\\.", "/");
      else {
        basePackage = "";
      }

      File dir = new File(srcFolder + basePackage + packageSuffix);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      if ("Launcher.java".equals(fileNameSuffix))
        os = FileUtils.openOutputStream(new File(dir, entityMeta.getProjectMeta().getMainClassName() + fileNameSuffix));
      else {
        os = FileUtils.openOutputStream(new File(dir, entityMeta.getClassName() + fileNameSuffix));
      }

      Writer writer = new OutputStreamWriter(os, Charset.forName("UTF-8"));
      Map map = new HashMap();
      map.put("entityMeta", entityMeta);
      execute(templatePath, map, writer);
      writer.flush();
    } finally {
      IOUtils.closeQuietly(os);
    }
  }


  public void execute(String templatePath, Map<String, Object> map, Writer writer)
  {
    VelocityEngine ve = new VelocityEngine();
    Properties p = new Properties();
    p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

    ClassLoader previous = Thread.currentThread().getContextClassLoader();
    try {
//      Bundle bundle = CodeGenPlugin.getDefault().getBundle();
//      ClassLoader bundleClassLoader = BundleUtils.getBundleClassLoader(bundle);
//      Thread.currentThread().setContextClassLoader(bundleClassLoader);
      ve.init(p);
    } catch (Exception e) {
      throw new RuntimeException("Velocity引擎初始化出错，" + e.getMessage(), e);
    } finally {
      Thread.currentThread().setContextClassLoader(previous);
    }
    Context vc = new VelocityContext();
    vc.put("dateTool", new DateTool());
    vc.put("numberTool", new NumberTool());
    vc.put("escapeTool", new EscapeTool());

    for (String key : map.keySet()) {
      vc.put(key, map.get(key));
    }

    try
    {
      Template tpl = ve.getTemplate(templatePath, "UTF-8");

      tpl.merge(vc, writer);
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("Velocity engine error" + e.getMessage(), e);
    } catch (ParseErrorException e) {
      throw new RuntimeException("Velocity engine error" + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Velocity engine error" + e.getMessage(), e);
    }
  }

}