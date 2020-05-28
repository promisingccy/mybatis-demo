package com.ccy.mybatisdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ccy.mybatisdemo.dao.UserMapper;
import com.ccy.mybatisdemo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SimpleTest
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/26 19:31
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Resource
    private UserMapper userMapper;

    /**
     * ************** 条件构造器查询 ***************
     *  queryWrapper.like("name", "雨") //%雨%
     *                 .likeRight("name", "雨") //雨%
     *                 .likeLeft("name", "雨") //%雨
     *                 .or() // or
     *                 .orderByDesc("age") //降序
     *                 .between("age", 20, 40)
     *                 .lt("age", 40) //<
     *                 .le("age", 20) //<=
     *                 .eq("age", 20) //=
     *                 .ge("age", 20) //>=
     *
     * */
    @Test
    public void selectByWrapper(){
        //名字中包含雨并且年龄小于40  name like '%雨%' and age<40
        //第一种wrapper构造方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //第二种wrapper构造方法
//        QueryWrapper<User> query = Wrappers.<User>query();

        //WHERE name LIKE ? AND age < ?
        queryWrapper.like("name", "雨") //%雨%
                .lt("age", 40); //<
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }



    /**
     * ************** 简单查询 ***************
     * */
    @Test
    //单个主键搜索
    public void selectById(){
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }

    @Test
    //多个主键查询
    public void selectByIds(){
        List<Long> idList = Arrays.asList(1265931886203580418L,1265926544883290113L,1265923834167431170L);
        List<User> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }

    @Test
    //
    public void selectByMap(){
        //map.put("name", "向南")
        //map.put("age", 28)
        //where name="向南" and age=28
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "向南");
        conditionMap.put("age", 28);
        //map的键是mysql的列名称 不是实体的属性名
        List<User> userList = userMapper.selectByMap(conditionMap);
        userList.forEach(System.out::println);
    }

    @Test
    //总数
    public void selectListCount(){
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(11, list.size());
        list.forEach(System.out::println);
    }


    /**
     * ************** 增加 ***************
     * */
    @Test
    //新增
    public void insert(){
        User user = new User();
        user.setName("向雨");
        user.setAge(48);
        user.setEmail("test@qq.com");
        user.setRemark("备注111");//非表字段
        user.setManagerId(1087982257332887553L);
        user.setCreateTime(LocalDateTime.now());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }


}
