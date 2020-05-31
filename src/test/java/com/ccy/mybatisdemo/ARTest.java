package com.ccy.mybatisdemo;

import com.ccy.mybatisdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @ClassName ARTest
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/31 16:51
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    @Test
    //新增
    public void insert(){
        User user = new User();
        user.setId(1267018455442288643L);// 设置存在的主键则更新 不存在则新增
        user.setName("向东");
        user.setAge(49);
        user.setEmail("test@qq.com");
        user.setRemark("备注111");//非表字段
        user.setManagerId(1087982257332887553L);
        user.setCreateTime(LocalDateTime.now());
        boolean rows = user.insertOrUpdate();
        System.out.println("影响记录数:" + rows);
    }
}
