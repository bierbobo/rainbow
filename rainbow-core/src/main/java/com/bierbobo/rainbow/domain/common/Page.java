package com.bierbobo.rainbow.domain.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2016/11/1.
 */
public class Page<T> implements Serializable {


    /**
     * 分页中的数据列表
     */
    protected List<T> data = new ArrayList<T>(); // 要返回的某一页列表


    //-- 排序顺序 --//
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    //-- 分页输入参数 --//
    private Integer pageNo = 1; // 当前页
    private Integer pageSize = -1; // 每页记录数
    private Integer totalCount = -1; // 总记录数


    //-- 分页结果参数
    private Integer totalPage; // 总页数
    private Integer startRow;  //开始行数
    private Integer endRow;    //结束行数


    private boolean isFirstPage; // 是否为第一页
    private boolean isLastPage; // 是否为最后一页
    private boolean hasPreviousPage; // 是否有前一页
    private boolean hasNextPage; // 是否有下一页


    private String orderBy = null;
    private String order = null;

    public Page<T>  setTotalCount(Integer totalCount) {

        this.totalCount = totalCount;
        if (totalCount!=null&&totalCount < 0) {
            this.totalPage = -1;
        }

        if(pageSize!=null&&totalCount!=null){
            if (totalCount%pageSize==0) {
                totalPage =  totalCount/pageSize;
            }else{
                totalPage =  totalCount/pageSize+1;
            }
        }
        return this;
    }

    public Page<T>  setPageNo(Integer pageNo) {
        if (pageNo!=null && pageNo < 1) {
            this.pageNo = 1;
        }else{
            this.pageNo = pageNo;
        }
        return this;
    }

    public Page<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    public Page<T> setStartRow(Integer startRow) {
        this.startRow = startRow;
        return this;
    }
    public Page<T> setEndRow(Integer endRow) {
        this.endRow = endRow;
        return this;
    }



    public Integer getTotalPage() {
        if (totalCount < 0) {
            return -1;
        }
        if(pageSize!=null){
            if (totalCount%pageSize==0) {
                totalPage =  totalCount/pageSize ;
            }else {
                totalPage = totalCount/pageSize+1;
            }
        }
        return totalPage;
    }

    /**
     * @return 开始行号，从1开始
     */
    public Integer getStartRow() {
        if (pageSize==null || pageNo==null) {
            startRow = 1;
        }else {
            startRow = pageSize * (pageNo - 1)+1;
        }
        return startRow;
    }

    public Integer getEndRow() {
        if(pageNo!=null&&pageSize!=null&&totalCount!=null){
            if (pageNo*pageSize>totalCount&&totalCount!=-1) {
                endRow = totalCount;
            }else {
                endRow = pageNo*pageSize;
            }
        }
        return endRow;
    }


    public Integer getStartRow(Integer pageNo,Integer pageSize){
        this.startRow = pageSize * (pageNo - 1)+1;
        return startRow;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static String getAsc() {
        return ASC;
    }

    public static String getDesc() {
        return DESC;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }



    public static void main(String[] args) {

        //分页查询实例

        List<Long> infoList=new ArrayList<>();

        Page<Long> page = new Page<Long>();
        page.setPageSize(10).setTotalCount(infoList.size());


        for(int pageNo =1;pageNo<=page.getTotalPage();pageNo++){

            page.setPageNo(pageNo);

            //1)
            List<Long> list=new ArrayList<Long>();
            for(int infoIndex=page.getStartRow() - 1;infoIndex<page.getEndRow();infoIndex++){
                list.add(infoList.get(infoIndex));
            }

            /*
            //2) sublist存在问题
            list.addAll(infoList.subList(page.getStartRow() - 1, page.getEndRow()));

            //3)
            QueryObject queryObject = new QueryObject();
            queryObject.setData(infoList.subList(page.getStartRow() - 1, page.getEndRow()));
            */

        }

    }

}
