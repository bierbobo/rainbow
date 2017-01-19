package com.bierbobo.rainbow.json;

import java.util.Date;
import com.fasterxml.jackson.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
/*

Jackson提供了一系列注解，方便对JSON序列化和反序列化进行控制，下面介绍一些常用的注解。

@JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。

@JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-study")。

@JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。
*/

public class User1 {
    private String name;

    //不JSON序列化年龄属性
    @JsonIgnore
    private Integer age;

    //格式化日期属性
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date birthday;

    //序列化email属性为mail
    @JsonProperty("mail")
    private String email;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}




class JacksonDemo1 {

    public static void main(String[] args) throws ParseException, IOException {
        User user = new User();
        user.setName("小民");
        user.setEmail("xiaomin@sina.com");
        user.setAge(20);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(dateformat.parse("1996-10-01"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
        //输出结果：{"name":"小民","birthday":"1996年09月30日","mail":"xiaomin@sina.com"}
    }
}