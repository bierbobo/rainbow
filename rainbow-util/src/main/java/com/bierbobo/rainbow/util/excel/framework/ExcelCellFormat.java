package com.bierbobo.rainbow.util.excel.framework;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by lifubo on 2016/12/27.
 */
public class ExcelCellFormat {


    private CellStyle cellStyle;
    private int cellType;


    //格式列: 字符串  数字 日期
    private String type;

    //日期格式 颜色 等等
    private String style;

    //默认值  ： 可用来代表标题列值
    private String titleName;
    //取值的属性名称
    private String propertyName;
    //取值的属性类型
    private String propertyType;
    //列的索引
    private String column;
    //是否可以为空
    private String isNull;



    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
