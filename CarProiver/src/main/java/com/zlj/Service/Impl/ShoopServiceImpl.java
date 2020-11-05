package com.zlj.Service.Impl;

import com.zlj.Config.RedisConfig;
import com.zlj.Dao.ShoopDao;
import com.zlj.Dto.ShoUpdateDto;
import com.zlj.Dto.ShoqueryDto;
import com.zlj.Entity.Shooping;
import com.zlj.Service.init.ShoopService;
import com.zlj.Vo.JsResult;
import com.zlj.third.RedissonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 21:33
 * description:
 */
@Service
public class ShoopServiceImpl implements ShoopService {

    @Autowired
    private ShoopDao dao;

    // 第一版购物车接口
    @Override
    public JsResult ShopV1(ShoqueryDto dto) {
        // 判断dto是否为空
        if(dto != null){
            // dto 不为空时
            if(dto.getSkuid() > 0 && dto.getUid() > 0 && dto.getScount() > 0){
                Shooping shooping = dao.queryByUid(dto);
                if(shooping == null){
                    // 之前没有在购物车添加过商品
                    if(dao.insertOne(dto) > 0){
                        return JsResult.ok();
                    }
                }else{
                    // 之前在购物车添加过商品
                    if(dao.update(dto) > 0){
                        return JsResult.ok();
                    }
                }
            }
        }
        return JsResult.fail();
    }

    // 添加单个商品的数量
    @Override
    public JsResult addGoods(ShoUpdateDto dto) {
        if(dto.getScount() < 99){
            if(dao.updateOne(dto) > 0){
                return JsResult.ok();
            }
        }
        return JsResult.fail();
    }

    // 减少单个商品的数量
    @Override
    public JsResult minusGoods(ShoUpdateDto dto) {
        if(dto.getScount() > 0){
            dto.setScount(-dto.getScount());
            if(dao.updateOne(dto) > 0){
                return JsResult.ok();
            }
        }
        return JsResult.fail();
    }

    // 删除商品
    @Override
    public JsResult delBySkuid(int id) {
        if(id > 0){
            if(dao.deleById(id) > 0){
                return JsResult.ok();
            }
        }
        return JsResult.fail();
    }

    @Override
    public JsResult ShopV2(ShoqueryDto dto) {



        return null;
    }

    @Override
    public JsResult addGoodsV2(ShoUpdateDto dto) {
        return null;
    }

    @Override
    public JsResult minusGoodsV2(ShoUpdateDto dto) {
        return null;
    }

    @Override
    public JsResult delBySkuidV2(ShoqueryDto dto) {
        if(dto != null){
            if(dto.getUid() > 0 && dto.getSkuid() > 0){
                if(RedissonUtil.checkKey(RedisConfig.SHOOP_UID + dto.getUid())){
                    // Key 存在
                    RedissonUtil.delHash(RedisConfig.SHOOP_UID + dto.getUid(),dto.getSkuid());
                }
                if(dao.deleByUid(dto) > 0){
                    return JsResult.ok();
                }
            }
        }
        return JsResult.fail();
    }

}
