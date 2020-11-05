package com.zlj.Dto;

import lombok.NoArgsConstructor;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 21:23
 * description:
 */
@NoArgsConstructor
public class ShoUpdateDto {

    private Integer uid;  // 用户id;
    private Integer scount;  // 存货

    public ShoUpdateDto(Integer uid, Integer scount) {
        this.uid = uid;
        this.scount = scount;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getScount() {
        return scount;
    }

    public void setScount(Integer scount) {
        this.scount = scount;
    }
}
