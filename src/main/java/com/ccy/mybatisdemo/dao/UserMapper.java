package com.ccy.mybatisdemo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy.mybatisdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/26 19:28
 * @Version 1.0
 **/
public interface UserMapper extends BaseMapper<User> {

    //注解方式 自定义sql方法
    @Select("select id,name from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER)Wrapper<User>wrapper);

    //xml配置方式
    IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER)Wrapper<User>wrapper);
}
