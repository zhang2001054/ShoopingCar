package com.zlj.Dao;

import com.zlj.Dto.ShoAddDto;
import com.zlj.Dto.ShoUpdateDto;
import com.zlj.Dto.ShoqueryDto;
import com.zlj.Entity.Shooping;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 21:06
 * description:
 */
@Component
public interface ShoopDao {

    // 查询skuid是否存在
    @Select("select * from t_shoup where skuid = #{skuid} and uid = #{uid}")
    Shooping queryByUid(ShoqueryDto dto);

    // 查询UID的全部信息
    @Select("select * from t_shoup where uid = #{uid}")
    List<Shooping> queryByUidAll(int uid);

    // 插入购物车数据
    @Insert("insert into t_shoup(skuid,uid,jprice,scount,ctime) value(#{skuid},#{uid},#{jprice},#{scount},#{ctime})")
    int insertOne(ShoqueryDto dto);

    // 添加商品数量
    @Update("uopdate t_shoup set scount = #{scount} where uid = #{uid} and skuid = #{skuid}")
    int update(ShoqueryDto dto);

    // 增加(或减少)单个商品
    @Update("update t_shoup set scount = scount + #{scount} where skuid = #{skuid} and uid = #{uid}")
    int updateOne(ShoUpdateDto dto);

    @Delete("delete from t_shoup where id = #{id}")
    int deleById(int id);

    @Delete("delete from t_shoup where uid = #{uid} and skuid = #{skuid}")
    int deleByUid(ShoqueryDto dto);
}
