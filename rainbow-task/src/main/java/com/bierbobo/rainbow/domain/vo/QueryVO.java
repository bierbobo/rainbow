package com.bierbobo.rainbow.domain.vo;

import java.io.Serializable;

/**
 * Created by lifubo on 2016/11/1.
 */
public class QueryVO implements Serializable {



    //前台传来的查询字段 。。。。

    /**
     * 登录人erp账号
     */
    private String loginErpCode;
    /**
     * 登录人部门id
     */
    private Integer deptId;


    private String sortField;//排序字段
    private String sortType;//排序：升序、降序；asc,desc



    private Integer pageNo;
    private Integer pageSize;
    private Integer startRow;
    private Integer totalCount;



    public Integer getStartRow() {
        if(pageNo!=null && pageNo>0  && pageSize!=null && pageSize>0){
            startRow = (pageNo-1)*pageSize;
        }else{
            startRow = 0;
        }
        return startRow;
    }



    public String getLoginErpCode() {
        return loginErpCode;
    }

    public void setLoginErpCode(String loginErpCode) {
        this.loginErpCode = loginErpCode;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
