package com.zlj.Dto;

import lombok.NoArgsConstructor;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 21:04
 * description:
 */
@NoArgsConstructor
public class ShoqueryDto {

    private Integer uid;  // 用户id;
    private Integer skuid;  // 商品的skuid
    private Integer scount;  // 存货
    private Integer jprice;  // 添加至购物车之前的价格
    private Integer ctime;   // 时间

    public ShoqueryDto(Integer uid, Integer skuid, Integer scount, Integer jprice, Integer ctime) {
        this.uid = uid;
        this.skuid = skuid;
        this.scount = scount;
        this.jprice = jprice;
        this.ctime = ctime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSkuid() {
        return skuid;
    }

    public void setSkuid(Integer skuid) {
        this.skuid = skuid;
    }

    public Integer getScount() {
        return scount;
    }

    public void setScount(Integer scount) {
        this.scount = scount;
    }

    public Integer getJprice() {
        return jprice;
    }

    public void setJprice(Integer jprice) {
        this.jprice = jprice;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}
