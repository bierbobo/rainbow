package com.bierbobo.code;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;


public class EntityMeta
{
  private String tableName;
  private String className;
  private String packageName;
  private Date generateDate;
  private String comment;
  private List<FieldMeta> fieldMetaList;
  private LinkedHashSet<String> importClassList;
  private ProjectMeta projectMeta;
  private FieldMeta pkFieldMeta;
  private int queryCount;
  private int lisCotunt;
  private int formItemCount;
  private int validateCount;
  private boolean relationship;
  private String velocitySymbol = "#";

  public String getTableName()
  {
    return this.tableName;
  }

  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }

  public String getClassName()
  {
    return this.className;
  }

  public void setClassName(String className)
  {
    this.className = className;
  }

  public String getVelocitySymbol() {
    return this.velocitySymbol;
  }

  public void setVelocitySymbol(String velocitySymbol) {
    this.velocitySymbol = velocitySymbol;
  }

  public String getPackageName()
  {
    return this.packageName;
  }

  public String getRootPackageName()
  {
    return this.packageName.substring(0, this.packageName.lastIndexOf("."));
  }

  public void setPackageName(String packageName)
  {
    this.packageName = packageName;
  }

  public Date getGenerateDate()
  {
    return this.generateDate;
  }

  public void setGenerateDate(Date generateDate)
  {
    this.generateDate = generateDate;
  }

  public List<FieldMeta> getFieldMetaList()
  {
    return this.fieldMetaList;
  }

  public void setFieldMetaList(List<FieldMeta> fieldMetaList)
  {
    this.fieldMetaList = fieldMetaList;
  }

  public String getComment()
  {
    return this.comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public FieldMeta getPkFieldMeta()
  {
    return this.pkFieldMeta;
  }

  public void setPkFieldMeta(FieldMeta pkFieldMeta)
  {
    this.pkFieldMeta = pkFieldMeta;
  }

  public String getLowerClassName() {
    if (StringUtils.isNotBlank(this.className)) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.className.substring(0, 1).toLowerCase());
      sb.append(this.className.substring(1));
      return sb.toString();
    }
    return this.className;
  }

  public LinkedHashSet<String> getImportClassList()
  {
    if (this.importClassList == null) {
      this.importClassList = new LinkedHashSet();
    }
    return this.importClassList;
  }

  public List<FieldMeta> getQueryPropertyList()
    throws Exception
  {
    List queryFieldMetaList = new ArrayList();
    if (this.fieldMetaList == null) {
      queryFieldMetaList = new ArrayList();
    }
    int count = 0;
    for (int i = 0; i < this.fieldMetaList.size(); i++) {
      FieldMeta fromFieldMeta = new FieldMeta();
      FieldMeta toFieldMeta = new FieldMeta();
      FieldMeta fieldMeta = (FieldMeta)this.fieldMetaList.get(i);

      if (fieldMeta.isQuery()) {
        if ("RANGE".equals(fieldMeta.getQueryType())) {
          String javaFieldName = fieldMeta.getUpperJavaFieldName();
          String comment = fieldMeta.getComment();
          fromFieldMeta = (FieldMeta)BeanUtils.cloneBean(fieldMeta);
          toFieldMeta = (FieldMeta)BeanUtils.cloneBean(fieldMeta);
          fromFieldMeta.setComment("开始" + comment);
          toFieldMeta.setComment("结束" + comment);
          fromFieldMeta.setJavaFieldName("from" + javaFieldName);
          toFieldMeta.setJavaFieldName("to" + javaFieldName);
          queryFieldMetaList.add(fromFieldMeta);
          queryFieldMetaList.add(toFieldMeta);
          count += 2;
        } else {
          count++;
          queryFieldMetaList.add(fieldMeta);
        }
      }
    }

    setQueryCount(count);
    return queryFieldMetaList;
  }

  public String getAuthor()
  {
    return System.getProperty("user.name");
  }

  public void addImportClass(String importClass)
  {
    getImportClassList().add(importClass);
  }

  public int getQueryCount() {
    return this.queryCount;
  }

  public void setQueryCount(int queryCount) {
    this.queryCount = queryCount;
  }

  public int getLisCotunt() {
    return this.lisCotunt;
  }

  public void setLisCotunt(int lisCotunt) {
    this.lisCotunt = lisCotunt;
  }

  public int getFormItemCount() {
    return this.formItemCount;
  }

  public void setFormItemCount(int formItemCount) {
    this.formItemCount = formItemCount;
  }

  public int getValidateCount() {
    return this.validateCount;
  }

  public void setValidateCount(int validateCount) {
    this.validateCount = validateCount;
  }

  public boolean isRelationship() {
    return this.relationship;
  }

  public void setRelationship(boolean relationship) {
    this.relationship = relationship;
  }

  public String toString()
  {
    return "[" + this.tableName + ", " + this.className + "]";
  }

  public ProjectMeta getProjectMeta() {
    return this.projectMeta;
  }

  public void setProjectMeta(ProjectMeta projectMeta) {
    this.projectMeta = projectMeta;
  }
}