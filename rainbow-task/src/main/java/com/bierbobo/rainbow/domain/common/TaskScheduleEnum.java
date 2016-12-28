package com.bierbobo.rainbow.domain.common;

/**
 * Created by lifubo on 2016/11/7.
 */
public enum TaskScheduleEnum {

    SERIAL(0,"Serial串行执行"),CONCURRENT(1,"Concurrent并行执行");

    private Integer type;
    private String msg;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    TaskScheduleEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static TaskScheduleEnum getByState(Integer type) {
        for (TaskScheduleEnum ens : values()) {
            if (ens.getType().equals(type)) {
                return ens;
            }
        }
        return null;
    }


}
