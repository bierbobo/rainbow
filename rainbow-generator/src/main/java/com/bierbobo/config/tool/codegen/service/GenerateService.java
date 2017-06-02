package com.bierbobo.config.tool.codegen.service;

import com.bierbobo.config.tool.codegen.domain.EntityMeta;
import java.io.IOException;
import java.util.List;

public abstract interface GenerateService
{
  public abstract String generateDdl(String paramString, List<EntityMeta> paramList);

  public abstract void generateDomainClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateConditionClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateMapperFile(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateDaoInterfaceClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateManagerInterfaceClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateManagerImplClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateServiceInterfaceClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateServiceImplClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateManagerTestClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateServiceTestClass(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateMybatisConfig(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateSpringDao(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateSpringMain(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateLauncher(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateSpringPublish(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateStartShCript(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateStopShCript(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateServiceTest(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateSpringWebRef(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateSpringRef(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateSpringTestRef(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateSpringWEBMain(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateSpringWeb(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateI18n(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateServiceI18n(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateVelocityPage(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateVelocityDefaultPage(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateJavaScript(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateJavaScriptCommonFn(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateController(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateExcelConstant(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateMainController(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateLog4jConfig(String paramString, EntityMeta paramEntityMeta)
    throws IOException;

  public abstract void generateSpringUmp(String paramString, List<EntityMeta> paramList)
    throws IOException;

  public abstract void generateProfilerUmp(String paramString, List<EntityMeta> paramList)
    throws IOException;
}