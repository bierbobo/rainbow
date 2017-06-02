package com.bierbobo.domain;

public class ProjectMeta
{
  private String projectName;
  private String groupId;
  private String artifactId;
  private String domainName;
  private String mainClassName;
  private boolean i18n;

  public String getModuleName()
  {
    return getMainClassName().substring(0, 1).toLowerCase() + getMainClassName().substring(1);
  }

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getArtifactId() {
    return this.artifactId;
  }

  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  public String getDomainName() {
    return this.domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public String getMainClassName() {
    return this.mainClassName;
  }

  public void setMainClassName(String mainClassName) {
    this.mainClassName = mainClassName;
  }

  public boolean isI18n() {
    return this.i18n;
  }

  public void setI18n(boolean i18n) {
    this.i18n = i18n;
  }
}