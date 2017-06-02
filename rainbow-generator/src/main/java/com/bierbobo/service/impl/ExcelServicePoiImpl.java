package com.bierbobo.service.impl;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bierbobo.domain.EntityMeta;
import com.bierbobo.domain.FieldMeta;
import com.bierbobo.domain.ProjectMeta;
import com.bierbobo.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelServicePoiImpl
  implements ExcelService
{
  private static final String TINYINT = "tinyint";
  private static final String STRING = "String";
  private static final String VARCHAR = "varchar";
  private static final String DATE = "Date";
  private static final String BigDecimal = "BigDecimal";
  private static final String BYTE = "byte";
  private static final String DATETIME = "datetime";
  private static final String YN = "yn";
  private static final String UPDATE_USER = "update_user";
  private static final String CREATE_USER = "create_user";
  private static final String UPDATE_TIME = "update_time";
  private static final String CREATE_TIME = "create_time";
  public static final String RANGE = "RANGE";

  public List<EntityMeta> parseExcel(String filePath)
    throws IOException
  {
    List entityMetaList = new ArrayList();

    InputStream is = null;
    try {
      is = new FileInputStream(filePath);
      XSSFWorkbook xwb = new XSSFWorkbook(is);
      ProjectMeta projectMeta = parseProjectMetaSheet(xwb);

      int i = 0;
      for (XSSFSheet sheet : xwb) {
        i++;
        if (i > 1) {
          EntityMeta entityMeta = parseSheet(sheet);
          entityMeta.setProjectMeta(projectMeta);

          entityMetaList.add(entityMeta);
        }
      }
    }
    finally {
      if (is != null)
        try {
          is.close();
        }
        catch (Exception localException1)
        {
        }
    }
    return entityMetaList;
  }

  private ProjectMeta parseProjectMetaSheet(XSSFWorkbook xwb) {
    ProjectMeta projectMeta = new ProjectMeta();
    XSSFSheet sheet = xwb.getSheetAt(0);
    XSSFRow row_projectName = sheet.getRow(0);
    XSSFRow row_artifactId = sheet.getRow(1);
    XSSFRow row_groupId = sheet.getRow(2);
    XSSFRow row_domainName = sheet.getRow(3);
    XSSFRow row_mainClassName = sheet.getRow(4);
    XSSFRow row_i18nName = sheet.getRow(5);

    XSSFCell cell_projectName = row_projectName.getCell(1);
    XSSFCell cell_artifactId = row_artifactId.getCell(1);
    XSSFCell cell_groupId = row_groupId.getCell(1);
    XSSFCell cell_domainName = row_domainName.getCell(1);
    XSSFCell cell_mainClassName = row_mainClassName.getCell(1);
    XSSFCell cell_i18nName = row_i18nName.getCell(1);

    String projectName = getCellValue(cell_projectName);
    String artifactId = getCellValue(cell_artifactId);
    String groupId = getCellValue(cell_groupId);
    String domainName = getCellValue(cell_domainName);
    String mainClassName = getCellValue(cell_mainClassName);
    String i18nName = getCellValue(cell_i18nName);

    projectMeta.setProjectName(projectName);
    projectMeta.setArtifactId(artifactId);
    projectMeta.setGroupId(groupId);
    projectMeta.setDomainName(domainName);
    projectMeta.setMainClassName(mainClassName);
    if ("YES".equalsIgnoreCase(i18nName)) {
      projectMeta.setI18n(true);
    }
    return projectMeta;
  }

  public EntityMeta parseSheet(XSSFSheet sheet)
    throws IOException
  {
    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
    if (physicalNumberOfRows == 0) {
      return null;
    }
    EntityMeta entityMeta = new EntityMeta();

    List fieldMetaList = new ArrayList();
    entityMeta.setFieldMetaList(fieldMetaList);
    Map fieldMetaMap = new HashMap();
    FieldMeta pkFieldMeta = null;

    int queryCount = 0;
    int lisCotunt = 0;
    int formItemCount = 0;
    int validateCount = 0;
    boolean rangeCreateTime = false;
    for (int i = sheet.getFirstRowNum(); i < physicalNumberOfRows; i++) {
      if ((i == sheet.getFirstRowNum()) || (i == sheet.getFirstRowNum() + 2)) {
        continue;
      }
      if (i == sheet.getFirstRowNum() + 1) {
        XSSFRow row = sheet.getRow(i);
        String tableName = getCellValue(row.getCell(0));
        String javaClassName = getCellValue(row.getCell(1));
        String javaPackageName = getCellValue(row.getCell(2));
        String comment = getCellValue(row.getCell(3));
        entityMeta.setTableName(tableName);
        entityMeta.setClassName(javaClassName);
        entityMeta.setPackageName(javaPackageName);
        entityMeta.setGenerateDate(new Date());
        entityMeta.setComment(comment);
        if ("YES".equals(getCellValue(row.getCell(4)))) {
          rangeCreateTime = true;
        }
        if ("NO".equals(getCellValue(row.getCell(4))))
          entityMeta.setRelationship(false);
        else {
          entityMeta.setRelationship(true);
        }
      }
      else
      {
        XSSFRow row = sheet.getRow(i);
        if (row == null) {
          continue;
        }
        FieldMeta fieldMeta = new FieldMeta();

        for (int j = row.getFirstCellNum(); j < 18; j++)
        {
          XSSFCell cell = row.getCell(j);
          String cellValue = getCellValue(cell);

          switch (j) {
          case 0:
            fieldMeta.setColumnName(cellValue);
            break;
          case 1:
            fieldMeta.setSqlType(cellValue);
            break;
          case 2:
            if (StringUtils.isBlank(cellValue))
              fieldMeta.setColumnLength(null);
            else {
              fieldMeta.setColumnLength(cellValue);
            }
            break;
          case 3:
            fieldMeta.setJavaFieldName(cellValue);
            break;
          case 4:
            fieldMeta.setJavaType(cellValue);
            if (cellValue.equals("Date")) {
              entityMeta.addImportClass("java.util.Date"); } else {
              if (!cellValue.equals("BigDecimal")) continue;
              entityMeta.addImportClass("java.math.BigDecimal");
            }
            break;
          case 5:
            fieldMeta.setComment(cellValue);
            break;
          case 6:
            fieldMeta.setTestValue(cellValue);
            break;
          case 7:
            if ("NO".equalsIgnoreCase(cellValue))
              fieldMeta.setAllowNull(false);
            else {
              fieldMeta.setAllowNull(true);
            }
            break;
          case 8:
            if ("YES".equalsIgnoreCase(cellValue)) {
              fieldMeta.setPk(true);
              if (pkFieldMeta != null) {
                throw new RuntimeException("不支持复合主键!");
              }
              pkFieldMeta = fieldMeta;
            } else {
              fieldMeta.setPk(false);
            }
            break;
          case 9:
            if ("YES".equalsIgnoreCase(cellValue))
              fieldMeta.setAutoIncrement(true);
            else {
              fieldMeta.setAutoIncrement(false);
            }
            break;
          case 10:
            if ("YES".equalsIgnoreCase(cellValue))
              fieldMeta.setUnique(true);
            else {
              fieldMeta.setUnique(false);
            }
            break;
          case 11:
            if ("YES".equalsIgnoreCase(cellValue)) {
              fieldMeta.setShowCols(true);
              lisCotunt++;
            } else {
              fieldMeta.setShowCols(false);
            }
            break;
          case 12:
            if ("YES".equalsIgnoreCase(cellValue)) {
              fieldMeta.setQuery(true);
              queryCount++;
            } else {
              fieldMeta.setQuery(false);
            }
            break;
          case 13:
            if ((cellValue == null) || ("".equals(cellValue))) {
              cellValue = "LIKE";
            }
            fieldMeta.setQueryType(StringUtils.upperCase(cellValue));
            break;
          case 14:
            if ("YES".equalsIgnoreCase(cellValue)) {
              fieldMeta.setFormItem(true);
              formItemCount++;
            } else {
              fieldMeta.setFormItem(false);
            }
            break;
          case 15:
            if ((cellValue == null) || ("".equals(cellValue))) {
              cellValue = "TEXT";
            }
            fieldMeta.setFormType(StringUtils.upperCase(cellValue));
            break;
          case 16:
            if (StringUtils.isNotEmpty(cellValue)) {
              validateCount++;
            }
            fieldMeta.setValidateCondition(cellValue);
            break;
          case 17:
            fieldMeta.setValidateMessage(cellValue);
          }

        }

        fieldMetaList.add(fieldMeta);
        fieldMetaMap.put(fieldMeta.getColumnName(), fieldMeta);
      }
    }

    if (pkFieldMeta != null)
      entityMeta.setPkFieldMeta(pkFieldMeta);
    else {
      throw new RuntimeException("表[" + entityMeta.getTableName() + "]至少有一个主键。");
    }

    if (fieldMetaMap.get("create_time") == null) {
      if (rangeCreateTime) {
        queryCount++;
      }
      fieldMetaList.add(createDefaultCreateTimeField(rangeCreateTime));
    }
    if (fieldMetaMap.get("update_time") == null) {
      fieldMetaList.add(createDefaultUpdateTimeField());
    }
    if (fieldMetaMap.get("create_user") == null) {
      fieldMetaList.add(createDefaultCreateUserField());
    }
    if (fieldMetaMap.get("update_user") == null) {
      fieldMetaList.add(createDefaultUpdateUserField());
    }
    if (fieldMetaMap.get("yn") == null) {
      fieldMetaList.add(createDefaultYnField());
    }

    entityMeta.addImportClass("java.util.Date");

    entityMeta.setQueryCount(queryCount);
    entityMeta.setLisCotunt(lisCotunt);
    entityMeta.setFormItemCount(formItemCount);
    entityMeta.setValidateCount(validateCount);
    return entityMeta;
  }

  private FieldMeta createDefaultCreateTimeField(boolean rangeCreateTime)
  {
    FieldMeta createTimeField = new FieldMeta();
    createTimeField.setColumnName("create_time");
    createTimeField.setSqlType("datetime");
    createTimeField.setColumnLength("");

    createTimeField.setJavaFieldName("createTime");
    createTimeField.setJavaType("Date");
    createTimeField.setComment("创建时间");
    createTimeField.setTestValue("");

    createTimeField.setAllowNull(false);
    createTimeField.setPk(false);
    createTimeField.setAutoIncrement(false);
    if (rangeCreateTime) {
      createTimeField.setQuery(true);
      createTimeField.setQueryType("RANGE");
    }
    createTimeField.setFormItem(false);
    createTimeField.setShowCols(true);
    createTimeField.setFormType("DATE");
    return createTimeField;
  }

  private FieldMeta createDefaultUpdateTimeField()
  {
    FieldMeta createTimeField = new FieldMeta();
    createTimeField.setColumnName("update_time");
    createTimeField.setSqlType("datetime");
    createTimeField.setColumnLength("");

    createTimeField.setJavaFieldName("updateTime");
    createTimeField.setJavaType("Date");
    createTimeField.setComment("更新时间");
    createTimeField.setTestValue("");

    createTimeField.setAllowNull(false);
    createTimeField.setPk(false);
    createTimeField.setAutoIncrement(false);

    createTimeField.setQuery(false);
    createTimeField.setQueryType("RANGE");
    createTimeField.setShowCols(false);
    createTimeField.setFormItem(false);

    return createTimeField;
  }

  private FieldMeta createDefaultCreateUserField()
  {
    FieldMeta createTimeField = new FieldMeta();
    createTimeField.setColumnName("create_user");
    createTimeField.setSqlType("varchar");
    createTimeField.setColumnLength("50");

    createTimeField.setJavaFieldName("createUser");
    createTimeField.setJavaType("String");
    createTimeField.setComment("创建人");
    createTimeField.setTestValue("");

    createTimeField.setAllowNull(true);
    createTimeField.setPk(false);
    createTimeField.setAutoIncrement(false);

    createTimeField.setQuery(false);
    createTimeField.setShowCols(false);
    createTimeField.setFormItem(false);

    return createTimeField;
  }

  private FieldMeta createDefaultUpdateUserField()
  {
    FieldMeta createTimeField = new FieldMeta();
    createTimeField.setColumnName("update_user");
    createTimeField.setSqlType("varchar");
    createTimeField.setColumnLength("50");

    createTimeField.setJavaFieldName("updateUser");
    createTimeField.setJavaType("String");
    createTimeField.setComment("更新人");
    createTimeField.setTestValue("");

    createTimeField.setAllowNull(true);
    createTimeField.setPk(false);
    createTimeField.setAutoIncrement(false);

    createTimeField.setQuery(false);
    createTimeField.setShowCols(false);
    createTimeField.setFormItem(false);

    return createTimeField;
  }

  private FieldMeta createDefaultYnField()
  {
    FieldMeta createTimeField = new FieldMeta();
    createTimeField.setColumnName("yn");
    createTimeField.setSqlType("tinyint");
    createTimeField.setColumnLength("");

    createTimeField.setJavaFieldName("yn");
    createTimeField.setJavaType("byte");
    createTimeField.setComment("删除标志");
    createTimeField.setTestValue("");

    createTimeField.setAllowNull(false);
    createTimeField.setPk(false);
    createTimeField.setAutoIncrement(false);

    createTimeField.setQuery(false);
    createTimeField.setShowCols(false);
    createTimeField.setFormItem(false);

    return createTimeField;
  }

  private String getCellValue(XSSFCell cell)
  {
    if (cell == null)
      return "";
    String cellValue;
    String cellValue;
    String cellValue;
    switch (cell.getCellType())
    {
    case 0:
      String cellValue;
      if (HSSFDateUtil.isCellDateFormatted(cell))
      {
        Date date = cell.getDateCellValue();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cellValue = sdf.format(date);
      }
      else
      {
        Integer num = new Integer((int)cell.getNumericCellValue());
        cellValue = String.valueOf(num);
      }
      break;
    case 1:
      cellValue = cell.getStringCellValue().replaceAll("'", "''");
      break;
    default:
      cellValue = " ";
    }

    return cellValue;
  }
}