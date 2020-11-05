package com.zlj.Dto;

import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/5 19:59
 * description:
 */
@NoArgsConstructor
public class MsgDto implements Serializable {

    private long id; // 唯一id
    private Integer type;  // 消息类型
    private Object data; // 消息的内容

    public MsgDto() {
    }

    public MsgDto(long id, Integer type, Object data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
