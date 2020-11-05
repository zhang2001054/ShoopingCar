package com.zlj.Service.Impl;

import com.zlj.Config.RabbitMQconfig;
import com.zlj.Config.RedisConfig;
import com.zlj.Dto.MsgDto;
import com.zlj.Dao.ShoopDao;
import com.zlj.Dto.ShoUpdateDto;
import com.zlj.Dto.ShoqueryDto;
import com.zlj.Entity.Shooping;
import com.zlj.Service.init.ShoopService;
import com.zlj.Utils.IdGeneratorSinglon;
import com.zlj.Vo.JsResult;
import com.zlj.third.RedissonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RabbitTemplate template;

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
        String key = RedisConfig.SHOOP_UID + dto.getUid();
        MsgDto msgDto = new MsgDto();
        // 判断Redis中是否有这个用户
        if(RedissonUtil.checkKey(key)){
            // 用户存在Redis中
            if(RedissonUtil.checkField(key,dto.getSkuid() + "")){
                // 根据Key获取到 Skuid的值
                Shooping shooping = (Shooping) RedissonUtil.getHash(key, dto.getSkuid() + "");
                // 添加同一个已存在的商品数量
                shooping.setScount(shooping.getScount() + dto.getScount());
                RedissonUtil.setHash(key,dto.getSkuid() + "",shooping);
                msgDto.setType(RabbitMQconfig.MQ_SHOP_UPDATE);
            }else{
                // 商品第一次加入
                Shooping shooping = (Shooping) RedissonUtil.getHash(key, dto.getSkuid() + "");
                RedissonUtil.setHash(key,dto.getSkuid()+"",shooping);
                msgDto.setType(RabbitMQconfig.MQ_SHOP_ADD);
            }
        }else{   // 用户不存在Redis中

            // 查询该用户的购物车内的商品
            List<Shooping> shoopings = dao.queryByUidAll(dto.getUid());
            if(shoopings == null){
                // 购物车内无商品
                Shooping shooping = new Shooping(dto.getUid(),dto.getSkuid(),dto.getScount(),dto.getJprice());
                RedissonUtil.setHash(key,dto.getSkuid()+"",shooping);
                msgDto.setType(RabbitMQconfig.MQ_SHOP_ADD);
            }else{
                // 购物车内有商品
                Map<String,Object> map = new HashMap<>();
                boolean ress = true;
                // 将数据库中的数据添加到Map集合中
                for (Shooping shop :shoopings){
                    // 循环判断 是否将该数据添加到购物车中
                    if(shop.getSkuid().equals(dto.getSkuid())){
                        shop.setScount(shop.getScount() + dto.getScount());
                        msgDto.setType(RabbitMQconfig.MQ_SHOP_UPDATE);
                    }
                    // 将循环的数据添加到Map集合中
                    map.put(dto.getSkuid()+"",shop);
                }
                // 更新Redis中的数据
                RedissonUtil.serHashAll(key,map);
            }
            // 设置有效期
            RedissonUtil.setTime(key,RedisConfig.SHOOP_TIME, TimeUnit.SECONDS);
        }
        msgDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
        msgDto.setData(dto);
        template.convertAndSend("");
        return JsResult.fail();
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
