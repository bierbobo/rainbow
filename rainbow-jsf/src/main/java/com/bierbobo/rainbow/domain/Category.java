package com.bierbobo.rainbow.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jd.gms.common.util.ErrorCodes;
import jd.gms.common.util.validation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 类目对象类
 * @author wuxianyuan
 *
 */
public class Category extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5524963660478833045L;
    /**
     * 分类编号
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类别名
     */
    private String aliasname;
    /**
     * 父分类编号
     */
    private Integer fid;
    /**
     * 分类等级
     */
    private Integer cataClass;
    /**
     * 上下柜状态
     */
    private String uplowstate;
    /**
     * 排序
     */
    private Integer orderSort;

    /**
     * 是否提供安装服务
     */
    private Integer isFitservice;

    /**
     * 状态
     */
    private Integer yn;
    /**
     * 预留字段
     */
    private String feature;
    /***
     * 小图
     */
    private String img;
    /***
     * 版本
     */
    private Integer version;
    /***
     * 创建日期
     */
    private Date created;
    /***
     * 修改日期
     */
    private Date modified;

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private List<Category> sons;

    public List<Category> getSons() {
        return sons;
    }

    public void setSons(List<Category> sons) {
        this.sons = sons;
    }

    /**
     * 分类编号
     */
    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_ID_MUST, groups = { Update.class,Delete.class,Get.class,TuanUpdate.class,SetAttributes.class})
    @Min(value = 1, message = ErrorCodes.CATEGORY_PRODUCTSORT_ID_GT0, groups = { Update.class, Delete.class, Get.class, GetList.class })
    public Integer getId() {
        return id;
    }

    /**
     * 分类编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_NAME_MUST, groups = { New.class })
    @RealLength(min = 1, max = 50, message = ErrorCodes.CATEGORY_PRODUCTSORT_NAME_LENGTH, groups = { New.class, Update.class })
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_FID_MUST, groups = { New.class })
    @Min(value = 0, message = ErrorCodes.CATEGORY_PRODUCTSORT_FID_GE0, groups = { New.class, GetList.class, Update.class })
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_UPLOWSTATE_MUST, groups = { New.class,TuanUpdate.class})
    @Min(value = 0, message = ErrorCodes.CATEGORY_PRODUCTSORT_UPLOWSTATE_GE0, groups = { New.class, Update.class, GetList.class })
    public String getUplowstate() {
        return uplowstate;
    }

    public void setUplowstate(String uplowstate) {
        this.uplowstate = uplowstate;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_YN_MUST, groups = { New.class,SetAttributes.class })
    @Min(value = 0, message = ErrorCodes.CATEGORY_PRODUCTSORT_YN_GE0, groups = { New.class, GetList.class, Update.class })
    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_CATACLASS_MUST, groups = { New.class })
    @Min(value = 0, message = ErrorCodes.CATEGORY_PRODUCTSORT_CATACLASS_GE0, groups = { New.class, GetList.class })
    public Integer getCataClass() {
        return cataClass;
    }

    public void setCataClass(Integer cataClass) {
        this.cataClass = cataClass;
    }

    @NotNull(message = ErrorCodes.CATEGORY_PRODUCTSORT_ORDERSORT_MUST, groups = { New.class })
    @Min(value = 0, message = ErrorCodes.CATEGORY_PRODUCTSORT_ORDERSORT_GE0, groups = { New.class, GetList.class, Update.class })
    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }

    public Integer getIsFitservice() {
        return isFitservice;
    }

    public void setIsFitservice(Integer isFitservice) {
        this.isFitservice = isFitservice;
    }

}
