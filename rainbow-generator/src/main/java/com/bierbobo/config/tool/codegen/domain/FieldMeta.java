package com.bierbobo.config.tool.codegen.domain;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldMeta
{
  private static final Log log = LogFactory.getLog(FieldMeta.class);
  private String columnName;
  private String sqlType;
  private String columnLength;
  private boolean pk = false;

  private boolean unique = false;

  private boolean autoIncrement = false;

  private boolean allowNull = true;
  private String comment;
  private String testValue;
  private String javaFieldName;
  private String javaType;
  private boolean showCols;
  private boolean query;
  private String queryType;
  private boolean formItem;
  private String formType;
  private String validateCondition;
  private String validateMessage;

  public boolean isShowCols()
  {
    return this.showCols;
  }

  public void setShowCols(boolean showCols) {
    this.showCols = showCols;
  }

  public String getValidateCondition() {
    return this.validateCondition;
  }

  public void setValidateCondition(String validateCondition) {
    this.validateCondition = validateCondition;
  }

  public String getValidateMessage() {
    return this.validateMessage;
  }

  public String getValidateDetailMessage() throws Exception {
    String message = "";
    try {
      if (StringUtils.isNotEmpty(this.validateMessage)) {
        Type type = new TypeToken() {
        }.getType();
        HashMap messages = (HashMap)new Gson().fromJson("{" + getValidateMessage() + "}", type);
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
          Map.Entry entry = (Map.Entry)iter.next();
          String key = (String)entry.getKey();
          String messageValue = (String)messages.get(key);
          message = message + "," + key + ":'" + this.comment + messageValue.replaceAll("\"", "") + "'";
        }
        if (message.length() > 0)
          message = message.substring(1);
      }
    }
    catch (JsonSyntaxException e) {
      log.error("验证格式或者样式消息格式不支持或者书写错误!" + e.getMessage());
      throw new Exception("验证格式或者样式消息格式不支持或者书写错误!" + e.getMessage());
    }
    return message;
  }

  public List<String> getServerValidateMessage() throws Exception {
    List list = new ArrayList();
    try {
      if ((this.validateCondition != null) && (!"".equals(this.validateCondition))) {
        Type type = new TypeToken() {
        }.getType();
        HashMap conditions = (HashMap)new Gson().fromJson(
          "{" + getValidateCondition() + "}", type);
        HashMap messages = (HashMap)new Gson().fromJson(
          "{" + getValidateDetailMessage() + "}", type);
        Iterator iter = conditions.entrySet().iterator();
        while (iter.hasNext()) {
          Map.Entry entry = (Map.Entry)iter.next();
          String key = (String)entry.getKey();
          String conditionValue = (String)conditions.get(key);
          ((String)messages.get(key));
          if ((!isNumber()) && (!isBoolean()))
            if ("required".equals(key))
            {
              list.add("@NotEmpty");
            }
            else if ("minlength".equals(key)) {
              list.add("@Length(min = " + conditionValue + ")");
            }
            else if ("maxlength".equals(key)) {
              list.add("@Length(max = " + conditionValue + ")");
            } else {
              if (!"email".equals(key))
                continue;
              list.add("@Email");
            }
        }
      }
    }
    catch (JsonSyntaxException e) {
      log.error("验证格式或者样式消息格式不支持或者书写错误!" + e.getMessage());
      throw new Exception("验证格式或者样式消息格式不支持或者书写错误!" + e.getMessage());
    }
    return list;
  }

  public void setValidateMessage(String validateMessage) {
    this.validateMessage = validateMessage;
  }

  public String getColumnName()
  {
    return this.columnName;
  }

  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }

  public String getSqlType()
  {
    if (this.sqlType != null) {
      return this.sqlType.toUpperCase();
    }
    return this.sqlType;
  }

  public boolean isQuery() {
    return this.query;
  }

  public void setQuery(boolean query) {
    this.query = query;
  }

  public String getQueryType() {
    return this.queryType;
  }

  public void setQueryType(String queryType) {
    this.queryType = queryType;
  }

  public boolean isFormItem() {
    return this.formItem;
  }

  public void setFormItem(boolean formItem) {
    this.formItem = formItem;
  }

  public String getFormType() {
    return this.formType;
  }

  public void setFormType(String formType) {
    this.formType = formType;
  }

  public String getJdbcType()
  {
    if ("DATETIME".equals(getSqlType()))
      return "TIMESTAMP";
    if ("INT".equals(getSqlType()))
      return "INTEGER";
    if ("TEXT".equals(getSqlType())) {
      return "CLOB";
    }
    return getSqlType();
  }

  public boolean isLowerNumber()
  {
    return ("int".equals(getJavaType())) || 
      ("double".equals(getJavaType())) || 
      ("float".equals(getJavaType())) || 
      ("long".equals(getJavaType())) || 
      ("byte".equals(getJavaType()));
  }

  public boolean isNumber()
  {
    return ("int".equals(getJavaType())) || 
      ("double".equals(getJavaType())) || 
      ("float".equals(getJavaType())) || 
      ("long".equals(getJavaType())) || 
      ("byte".equals(getJavaType())) || 
      ("long".equals(getJavaType())) || 
      ("Integer".equals(getJavaType())) || 
      ("Double".equals(getJavaType())) || 
      ("Float".equals(getJavaType())) || 
      ("Long".equals(getJavaType())) || 
      ("BigDecimal".equals(getJavaType())) || 
      ("Byte".equals(getJavaType()));
  }

  public boolean isLowerBoolean()
  {
    return "boolean".equals(getJavaType());
  }

  public boolean isBoolean()
  {
    return ("boolean".equals(getJavaType())) || ("Boolean".equals(getJavaType()));
  }

  public void setSqlType(String sqlType)
  {
    this.sqlType = sqlType;
  }

  public String getColumnLength()
  {
    return this.columnLength;
  }

  public void setColumnLength(String columnLength)
  {
    this.columnLength = columnLength;
  }

  public String getComment()
  {
    return this.comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getTestValue()
  {
    return this.testValue;
  }

  public void setTestValue(String testValue)
  {
    this.testValue = testValue;
  }

  public boolean isPk()
  {
    return this.pk;
  }

  public void setPk(boolean pk)
  {
    this.pk = pk;
  }

  public boolean isAutoIncrement()
  {
    return this.autoIncrement;
  }

  public void setAutoIncrement(boolean autoIncrement)
  {
    this.autoIncrement = autoIncrement;
  }

  public boolean isAllowNull()
  {
    return this.allowNull;
  }

  public void setAllowNull(boolean allowNull)
  {
    this.allowNull = allowNull;
  }

  public String getJavaFieldName()
  {
    return this.javaFieldName;
  }

  public void setJavaFieldName(String fieldName)
  {
    this.javaFieldName = fieldName;
  }

  public String getJavaType()
  {
    return this.javaType;
  }

  public void setJavaType(String javaType)
  {
    this.javaType = javaType;
  }

  public boolean isUnique() {
    return this.unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public String getUpperJavaFieldName()
  {
    if (StringUtils.isNotBlank(this.javaFieldName)) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.javaFieldName.substring(0, 1).toUpperCase());
      sb.append(this.javaFieldName.substring(1));
      return sb.toString();
    }
    return this.javaFieldName;
  }
}