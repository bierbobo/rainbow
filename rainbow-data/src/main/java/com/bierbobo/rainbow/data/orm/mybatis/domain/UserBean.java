package com.bierbobo.rainbow.data.orm.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class UserBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private Double account;
    private String creatorErp;
    private Date createTime;

    public UserBean() {
        super();
    }
    
    public UserBean(String username, String password, Double account) {
        super();
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public UserBean(Integer id, String username, String password, Double account) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public String getCreatorErp() {
        return creatorErp;
    }

    public void setCreatorErp(String creatorErp) {
        this.creatorErp = creatorErp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", account=" + account +
                ", creatorErp='" + creatorErp + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}