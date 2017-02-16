package com.bierbobo.rainbow.data.orm.mybatis.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lifubo on 2016/10/28.
 */
public class SubscibeTask implements Serializable {


    private String id;
    private String name;
    private String templateId;
    private String templateName;
    private int pushMode;
    private String pushTime;

    private int exportType; // 0:横向导出 ;1:纵向导出',
    private String describe;
    private String nextExecDate;
    private String creator;
    private String updator;
    private Date createTime;
    private Date updateTime;

    private String creatorName;
    private String userRole;     // manager  saleBuy
    private String email;


    //并发调度任务所用
    private Integer state;
    private String serverIp;
    private String message;

    private Integer taskNum;
    private List<Integer> sourceStateList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getPushMode() {
        return pushMode;
    }

    public void setPushMode(int pushMode) {
        this.pushMode = pushMode;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getExportType() {
        return exportType;
    }

    public void setExportType(int exportType) {
        this.exportType = exportType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getNextExecDate() {
        return nextExecDate;
    }

    public void setNextExecDate(String nextExecDate) {
        this.nextExecDate = nextExecDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public List<Integer> getSourceStateList() {
        return sourceStateList;
    }

    public void setSourceStateList(List<Integer> sourceStateList) {
        this.sourceStateList = sourceStateList;
    }
}



