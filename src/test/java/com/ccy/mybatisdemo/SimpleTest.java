package com.ccy.mybatisdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.ccy.mybatisdemo.dao.UserMapper;
import com.ccy.mybatisdemo.entity.User;
import org.apache.commons.lang3.StringUtils;
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

    //分页查询
    @Test
    public void selectByPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //第三个参数false则不查询总数
        Page<User> page = new Page<>(1, 2);
//        Page<User> page = new Page<>(1, 2, false);

/*      //第一种分页 返回 实体
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数" + iPage.getPages());
        List<User> userList = iPage.getRecords();
        */

/*
        //第二种分页  返回 Map
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数" + iPage.getPages());
        List<Map<String, Object>> userList = iPage.getRecords();
*/

        //自定义sql语句分页 selectUserPage
        IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数" + iPage.getPages());
        List<User> userList = iPage.getRecords();

        userList.forEach(System.out::println);
    }


    //自定义查询方法 selectAll
    @Test
    public void selectByMySql(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(User::getName, "王").lt(User::getAge, 40).isNotNull(User::getEmail);
        List<User> list = userMapper.selectAll(queryWrapper);
        list.forEach(System.out::println);
    }


    /**
     * ************** 条件构造器查询 ***************
     * 官网 https://mp.baomidou.com/guide/wrapper.html#abstractwrapper
     * */
    @Test
    public void selectLambdaChain(){
        //name LIKE ? AND age >= ?
        List<User> list = new LambdaQueryChainWrapper<>(userMapper)
                .like(User::getName, "雨")
                .ge(User::getAge, 20).list();
        list.forEach(System.out::println);
    }

    @Test
    public void selectLambda(){
        //************** lambdaQuery 不硬编码sql字段  **************
        //三种构造方法 ctrl + alt + v 自动补全
//        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<Object> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        //**SQL条件**
        // name like '王%' and (age<40 or email is not null)
        lambdaQuery.likeRight(User::getName, "王").and(lq->lq.lt(User::getAge, 40).or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectList(lambdaQuery);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs(){
        //************** selectObjs 只返回select的第一列的时候使用 **************
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.select("age", "id", "name").likeRight("name", "向")
                .and(wq->wq.lt("age", 40).or().isNotNull("email"));

        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps(){
        //************** selectMaps 返回不是list而是map **************
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();

        /**
         * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
         * 并且只取年龄总和小于500的组。
            select avg(age) avg_age,min(age) min_age,max(age) max_age
            from user
            group by manager_id
            having sum(age) <500
        */
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                .having("sum(age)<{0}", 500);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperAllEq(){
        //************** allEq 使用map作为条件参数 **************
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //WHERE name = ? AND age = ?
        Map<String, Object> params = new HashMap<>();
        params.put("name", "张飞");
        params.put("age", null);
        //queryWrapper.allEq(params, false);//false标识map中为null的属性将被忽略掉 //WHERE name = ?

        //WHERE age IS NULL 排除掉了map中不为name的条件
        queryWrapper.allEq((k,v)->!k.equals("name"), params);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEntity(){
        //************** 实体作为参数的构造器 **************
        //默认是 name=? AND age=?
        User whereUser = new User();
        whereUser.setName("向南");
        whereUser.setAge(28);

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>(whereUser);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper(){
        //第一种wrapper构造方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //第二种wrapper构造方法
//        QueryWrapper<User> query = Wrappers.<User>query();

        //**SQL条件**
//        String name = "王";
//        String email = "";
//        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name).like(StringUtils.isNotEmpty(email), "email", email);


        //**SQL字段**
        // 反选指定字段
        // select(User.class, info->info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id"))
//        queryWrapper.select(User.class, info->!info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id"))
//                .like("name", "雨") //%雨%
//                .lt("age", 40); //<

        //**SQL条件**
        // name LIKE ? AND age < ?
//        queryWrapper.like("name", "雨") //%雨%
//                .lt("age", 40); //<

        //**SQL条件**
        // date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
//        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")  //{0} 防止sql注入
//                .inSql("manager_id", "select id from user where name like '王%'");

        //**SQL条件**
        // name like '王%' and (age<40 or email is not null)
//        queryWrapper.likeRight("name", "王")
//                .and(wq->wq.lt("age", 40).or().isNotNull("email"));

        //**SQL条件**
        // name like '向%' or (age<40 and age>20 and email is not null)
//        queryWrapper.likeRight("name", "向")
//                .or(wq->wq.lt("age", 40).gt("age", 20).isNotNull("email"));

        //**SQL条件**
        // (age<40 or email is not null) and name like '王%'
//        queryWrapper.nested(wq->wq.lt("age", 40).isNotNull("email")).likeRight("name", "王");


        //**SQL条件**
        // age in (30、31、34、35)
//        queryWrapper.in("age", 30, 31, 34, 35);

        //**SQL条件**
        // age in (30、31、34、35) limit 1
//        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");

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

    /**
     * ************** 更新 ***************
     * */
    @Test
    public void updateById(){
        User user = new User();
        user.setId(1265944286311006209L);
        user.setAge(26);
        int rows = userMapper.updateById(user);
        System.out.println("影响记录数:" + rows);
    }

    @Test
    public void updateByWrapper(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "向南").eq("age", 35).set("email", "xiangnan@qq.com");

        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数:" + rows);
    }

    @Test
    public void updateByLambdaWrapper(){
/*
        //lambda调用
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getName, "李艺伟").set(User::getAge, 31).set(User::getEmail, "lywww@baomidou.com");
        int rows = userMapper.update(null, lambdaUpdateWrapper);
        System.out.println("影响记录数:" + rows);*/

        //lambda链式调用
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                        .eq(User::getName, "李艺伟")
                        .set(User::getAge, 32)
                        .set(User::getEmail, "lywss@baomidou.com")
                        .update();
        System.out.println(update);
    }

    /**
     * ************** 删除 ***************
     * */
    @Test
    public void deleteById(){
        int res = userMapper.deleteById(1265928508681490434L);
        System.out.println(res);
    }

    @Test
    public void deleteByExt(){
        //通过map获取操作对象
/*        Map<String, Object> map = new HashMap<>();
        map.put("name", "向南");
        map.put("age", 28);

        int res = userMapper.deleteByMap(map);
        System.out.println(res);*/

        //通过list获取id操作对象
        /*int num = userMapper.deleteBatchIds(Arrays.asList(1265251143211081730L, 1265923237842366465L,
                1265923834167431170L));
        System.out.println("删除条数：" + num);*/

        //通过lambda操作对象
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "李艺伟").eq(User::getAge, 32);
        int rows = userMapper.delete(lambdaQueryWrapper);
        System.out.println("影响记录数:" + rows);
    }

}
