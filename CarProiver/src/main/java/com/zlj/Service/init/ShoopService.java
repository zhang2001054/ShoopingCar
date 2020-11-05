package com.zlj.Service.init;

import com.zlj.Dto.ShoUpdateDto;
import com.zlj.Dto.ShoqueryDto;
import com.zlj.Vo.JsResult;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 21:30
 * description:
 */

public interface ShoopService {

    // 购物车第一版 V1.0
    JsResult ShopV1(ShoqueryDto dto);

    // 商品添加(加)
    JsResult addGoods(ShoUpdateDto dto);

    // 商品添加(减)
    JsResult minusGoods(ShoUpdateDto dto);

    // 删除商品
    JsResult delBySkuid(int id);


    // 购物车第一版 V2.0
    JsResult ShopV2(ShoqueryDto dto);

    // 商品添加(加)
    JsResult addGoodsV2(ShoUpdateDto dto);

    // 商品添加(减)
    JsResult minusGoodsV2(ShoUpdateDto dto);

    // 删除商品
    JsResult delBySkuidV2(ShoqueryDto dto);

}
