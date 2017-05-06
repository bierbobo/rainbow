package com.bierbobo.rainbow.data.orm.jdbc;

/**
 * Created by lifubo on 2017/4/27.
 */
public class TableColumn {


    //column_name, ORDINAL_POSITION, DATA_TYPE as column_type, COLUMN_KEY,  column_comment
    //列名、排序、数据类型、主键、注释
    private String columnName;
    private String columnPosition;
    private String columnType;
    private String columnKey;
    private String columnComment;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(String columnPosition) {
        this.columnPosition = columnPosition;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Override
    public String toString() {
        return "TableColumn{" +
                "columnName='" + columnName + '\'' +
                ", columnPosition='" + columnPosition + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnKey='" + columnKey + '\'' +
                ", columnComment='" + columnComment + '\'' +
                '}';
    }
}
