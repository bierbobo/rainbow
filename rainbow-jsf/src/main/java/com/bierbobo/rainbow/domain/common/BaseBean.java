//package com.bierbobo.rainbow.domain.common;
//
//import java.io.Serializable;
////import org.apache.commons.lang.StringUtils;
////import org.hibernate.validator.constraints.NotBlank;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import java.io.Serializable;
//
///**
// * 基础对象类
// * @author guzhongzheng
// *
// */
//public class BaseBean implements Serializable {
//
//    private static final long serialVersionUID = -2214928339244466010L;
//
//    /**
//     * 操作人
//     */
//    private String opName;
//
//    /**
//     * ip信息
//     */
//    private String ip;
//
//    /**
//     * 调用的应用
//     */
//    private ConsumerSources source;
//
//    /**
//     * 总行数
//     */
//    private int totalRows;
//
//    /**
//     * 每页显示的行数
//     */
//    private int pageSize;
//
//    /**
//     * 当前页号
//     */
//    private int currentPage = -1;
//
//
//    @NotBlank(message = ErrorCodes.ITEM_IDENTITY_OPNAME_MUST, groups = { New.class, Update.class, Delete.class })
//    public String getOpName() {
//        return opName;
//    }
//
//    public void setOpName(String opName) {
//        this.opName = opName;
//    }
//
//    @NotBlank(message = ErrorCodes.ITEM_IDENTITY_IP_MUST, groups = { New.class, Update.class, Delete.class })
//    @Pattern(message = ErrorCodes.ITEM_IDENTITY_IP_FORMAT, regexp = "(^(?:\\d{1,3}\\.){3}(?:\\d{1,3}.*)$)", groups = { New.class, Update.class })
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public int getTotalRows() {
//        return totalRows;
//    }
//
//    public void setTotalRows(int totalRows) {
//        this.totalRows = totalRows;
//    }
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public int getCurrentPage() {
//        return currentPage;
//    }
//
//    public void setCurrentPage(int currentPage) {
//        this.currentPage = currentPage;
//    }
//    @NotNull(message = ErrorCodes.ITEM_IDENTITY_SOURCE_MUST, groups = { New.class, Update.class, Delete.class })
//    public ConsumerSources getSource() {
//        return source;
//    }
//
//    public void setSource(ConsumerSources source) {
//        this.source = source;
//    }
//
//}
