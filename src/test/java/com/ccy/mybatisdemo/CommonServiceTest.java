package com.ccy.mybatisdemo;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ccy.mybatisdemo.entity.User;
import com.ccy.mybatisdemo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommonServiceTest
 * @Description //TODO
 * @Author ccy
 * @Date 2020/6/1 19:41
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne(){
        //WHERE age > 45
//        User res = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 45), false);

        //WHERE name = ?
        List<User> res = userService.list(Wrappers.<User>lambdaQuery().eq(User::getName, "ccy"));

        System.out.println(res);
    }

    @Test
    public void Batch(){
        User user1 = new User();
        user1.setName("ccy");
        user1.setAge(20);

        User user2 = new User();
        user2.setName("ccy");
        user2.setAge(22);

        List<User> userList = Arrays.asList(user1, user2);
        boolean b = userService.saveBatch(userList);
        System.out.println(b);
    }

}
