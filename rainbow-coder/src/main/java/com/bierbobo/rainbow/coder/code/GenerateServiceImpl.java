package com.bierbobo.rainbow.coder.code;


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


  public void generateServiceInterfaceClass(String srcFolder,EntityMeta entityMeta)
          throws IOException
  {
    generateFile(entityMeta, srcFolder,
            "/service", "Service.java", "java/ServiceInterface.java.vm");
  }

    public void generateServiceImplClass(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/service/impl", "ServiceImpl.java", "java/ServiceImpl.java.vm");
    }


    public void generateDaoInterfaceClass(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/dao", "Dao.java", "java/DaoInterface.java.vm");
    }

    public void generateMapperFile(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder, "/mapper/",
                "Mapper.xml", "mapper/EntityMapper.xml.vm", false);
    }


    public void generateManagerInterfaceClass(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/manager", "Manager.java", "java/ManagerInterface.java.vm");
    }

    public void generateManagerImplClass(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/manager/impl", "ManagerImpl.java", "java/ManagerImpl.java.vm");
    }





    public String generateDdl(String srcFolder, List<EntityMeta> entityMetaList)
    {
        StringBuilder sb = new StringBuilder();
        for (EntityMeta entityMeta : entityMetaList) {
            sb.append("create table ");
            sb.append(entityMeta.getTableName());
            sb.append(" (");

            List<FieldMeta> fieldMetaList = new ArrayList(entityMeta.getFieldMetaList());
            fieldMetaList.add(createDefaultTsField());
            fieldMetaList.add(createDefaultVersionField());
            fieldMetaList.add(createDefaultReserveField1());
            fieldMetaList.add(createDefaultReserveField2());
            fieldMetaList.add(createDefaultTestField());

            for (FieldMeta fieldMeta : fieldMetaList) {
                sb.append(fieldMeta.getColumnName());
                sb.append(" ").append(fieldMeta.getSqlType());
                if ((fieldMeta.getColumnLength() != null) && (!"".equals(fieldMeta.getColumnLength()))) {
                    sb.append("(").append(fieldMeta.getColumnLength()).append(")");
                }
                if (!fieldMeta.isAllowNull()) {
                    sb.append(" NOT NULL");
                }
                if (fieldMeta.isAutoIncrement()) {
                    sb.append(" AUTO_INCREMENT");
                }

                if (fieldMeta.isLowerNumber()) {
                    sb.append(" DEFAULT 0 ");
                }
                sb.append(" COMMENT '").append(fieldMeta.getComment()).append("',");
            }

            for (FieldMeta fieldMeta : fieldMetaList) {
                if (fieldMeta.isUnique())
                    sb.append("UNIQUE  INDEX  index_" + fieldMeta.getColumnName() + " (" + fieldMeta.getColumnName() + ") ").append(", ");
            }
            sb.append(" PRIMARY KEY (");
            sb.append(entityMeta.getPkFieldMeta().getColumnName());
            sb.append("))");

            sb.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='");
            sb.append(entityMeta.getComment());
            sb.append("';\n");
        }

        String sql = sb.toString();
        write(srcFolder, "createTable.sql", sql);

        return sql;
    }


    private void write(String targetPath, String fileName, String sql)
    {
        Reader reader = null;
        OutputStream os = null;
        try {
            reader = new StringReader(sql);
            File dir = new File(targetPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            os = FileUtils.openOutputStream(new File(dir, fileName));
            IOUtils.copy(reader, os, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private FieldMeta createDefaultVersionField()
    {
        FieldMeta defaultVersionField = new FieldMeta();
        defaultVersionField.setColumnName("version");
        defaultVersionField.setSqlType("tinyint");
        defaultVersionField.setColumnLength("");
        defaultVersionField.setJavaFieldName("version");
        defaultVersionField.setJavaType("int");
        defaultVersionField.setComment("版本号");
        defaultVersionField.setTestValue("");

        defaultVersionField.setAllowNull(true);
        defaultVersionField.setPk(false);
        defaultVersionField.setAutoIncrement(false);

        defaultVersionField.setQuery(false);
        defaultVersionField.setShowCols(false);
        defaultVersionField.setFormItem(false);

        return defaultVersionField;
    }

    private FieldMeta createDefaultReserveField1()
    {
        FieldMeta reserveField1 = new FieldMeta();
        reserveField1.setColumnName("reserve1");
        reserveField1.setSqlType("varchar");
        reserveField1.setColumnLength("100");
        reserveField1.setJavaFieldName("reserve1");
        reserveField1.setJavaType("String");
        reserveField1.setComment("预留字段1");
        reserveField1.setTestValue("");

        reserveField1.setAllowNull(true);
        reserveField1.setPk(false);
        reserveField1.setAutoIncrement(false);

        reserveField1.setQuery(false);
        reserveField1.setShowCols(false);
        reserveField1.setFormItem(false);

        return reserveField1;
    }

    private FieldMeta createDefaultReserveField2()
    {
        FieldMeta reserveField2 = new FieldMeta();
        reserveField2.setColumnName("reserve2");
        reserveField2.setSqlType("varchar");
        reserveField2.setColumnLength("100");
        reserveField2.setJavaFieldName("reserve2");
        reserveField2.setJavaType("String");
        reserveField2.setComment("预留字段2");
        reserveField2.setTestValue("");

        reserveField2.setAllowNull(true);
        reserveField2.setPk(false);
        reserveField2.setAutoIncrement(false);

        reserveField2.setQuery(false);
        reserveField2.setShowCols(false);
        reserveField2.setFormItem(false);
        return reserveField2;
    }

    private FieldMeta createDefaultTestField()
    {
        FieldMeta testField = new FieldMeta();
        testField.setColumnName("test");
        testField.setSqlType("tinyint");
        testField.setColumnLength("");
        testField.setJavaFieldName("test");
        testField.setJavaType("byte");
        testField.setComment("是否为测试数据");
        testField.setTestValue("");

        testField.setAllowNull(false);
        testField.setPk(false);
        testField.setAutoIncrement(false);

        testField.setQuery(false);
        testField.setShowCols(false);
        testField.setFormItem(false);

        return testField;
    }

    private FieldMeta createDefaultTsField()
    {
        FieldMeta tsField = new FieldMeta();
        tsField.setColumnName("ts");
        tsField.setSqlType("timestamp");

        tsField.setJavaFieldName("ts");
        tsField.setJavaType("Date");
        tsField.setComment("时间戳");
        tsField.setTestValue("");

        tsField.setAllowNull(false);
        tsField.setPk(false);
        tsField.setAutoIncrement(false);

        tsField.setQuery(false);
        tsField.setShowCols(false);
        tsField.setFormItem(false);

        return tsField;
    }



    private void generateFile(EntityMeta entityMeta, String filePath, String filePathSuffix, String fileNameSuffix, String templatePath)
            throws IOException
    {
        generateFile(entityMeta, filePath, filePathSuffix, fileNameSuffix, templatePath, true);
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

    public void generateManagerTestClass(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/manager", "ManagerTest.java", "java/ManagerTest.java.vm");
    }



    public void generateProfilerUmp(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder,
                "/", "profilerMap.xml", "profilerMap.xml.vm");
    }
    public void generateSpringPublish(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder,
                "/spring", "spring-publish.xml", "spring/spring-publish.xml.vm");
    }

    public void generateSpringMain(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateConfigFile(entityMeta, srcFolder,
                "/spring", "spring-main.xml", "spring/spring-main.xml.vm");
    }

    public void generateMybatisConfig(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder,
                "/spring", "mybatis-config.xml", "spring/mybatis-config.xml.vm");
    }
    public void generateSpringDao(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateConfigFile(entityMeta, srcFolder,
                "/spring", "spring-dao.xml", "spring/spring-dao.xml.vm");
    }

    public void generateLog4jConfig(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateConfigFile(entityMeta, srcFolder,
                "/", "log4j.properties", "log4j.properties.vm");
    }

    public void generateSpringUmp(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder,
                "/spring", "spring-ump.xml", "spring/spring-ump.xml.vm");
    }


    public void generateLauncher(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "", "Launcher.java", "java/Launcher.java.vm");
    }

    public void generateStartShCript(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateConfigFile(entityMeta, srcFolder, "", "start.sh", "bin/start.sh.vm");
    }

    public void generateStopShCript(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateConfigFile(entityMeta, srcFolder, "", "stop.sh", "bin/stop.sh.vm");
    }

    public void generateServiceTest(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder, "", "ServiceTest.java", "java/ServiceTest.java.vm");
    }

    public void generateSpringRef(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder, "/spring", "spring-ref.xml", "spring/spring-ref.xml.vm");
    }

    public void generateSpringTestRef(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder, "/spring", "spring-ref-test.xml", "spring/spring-ref-test.xml.vm");
    }

    public void generateSpringWebRef(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder, "/spring", "spring-ref.xml", "spring/spring-ref-web.xml.vm");
    }

    public void generateSpringWEBMain(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder, "/spring", "spring-main.xml", "spring/spring-main-web.xml.vm");
    }

    public void generateSpringWeb(String srcFolder, List<EntityMeta> entityMetaList)
            throws IOException
    {
        generateConfigFile(entityMetaList, srcFolder, "/spring", "spring-web.xml", "spring/spring-web.xml.vm");
    }

    public void generateController(String srcFolder, EntityMeta entityMeta)
            throws IOException
    {
        generateFile(entityMeta, srcFolder,
                "/controller", "Controller.java", "java/Controller.java.vm");
    }



    private void generateConfigFile(List<EntityMeta> entityMetaList, String filePath, String filePathSuffix, String fileName, String templatePath)
            throws IOException
    {
        FileOutputStream os = null;
        try {
            File dir = new File(filePath + filePathSuffix);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            os = FileUtils.openOutputStream(new File(dir, fileName));

            Writer writer = new OutputStreamWriter(os, Charset.forName("UTF-8"));
            Map map = new HashMap();
            map.put("entityMetaList", entityMetaList);
            execute(templatePath, map, writer);
            writer.flush();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private void generateConfigFile(EntityMeta entityMeta, String srcFolder, String packageSuffix, String fileName, String templatePath)
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
            map.put("entityMeta", entityMeta);
            execute(templatePath, map, writer);
            writer.flush();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }




}