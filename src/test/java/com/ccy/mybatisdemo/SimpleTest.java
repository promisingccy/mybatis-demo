package com.ccy.mybatisdemo;

import com.ccy.mybatisdemo.dao.UserMapper;
import com.ccy.mybatisdemo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    public void select(){
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(6, list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void insert(){
        User user = new User();
        user.setName("向南");
        user.setAge(27);
        user.setEmail("test@qq.com");
        user.setRemark("备注111");//非表字段
        user.setManagerId(1087982257332887553L);
        user.setCreateTime(LocalDateTime.now());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }


}
