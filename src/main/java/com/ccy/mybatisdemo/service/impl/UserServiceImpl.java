package com.ccy.mybatisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccy.mybatisdemo.dao.UserMapper;
import com.ccy.mybatisdemo.entity.User;
import com.ccy.mybatisdemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description //TODO
 * @Author ccy
 * @Date 2020/6/1 19:37
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
