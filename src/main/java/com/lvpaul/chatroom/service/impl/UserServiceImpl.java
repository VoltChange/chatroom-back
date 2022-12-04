package com.lvpaul.chatroom.service.impl;

import com.lvpaul.chatroom.entity.User;
import com.lvpaul.chatroom.entity.UserExample;
import com.lvpaul.chatroom.mapper.UserMapper;
import com.lvpaul.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String test() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(1);
        return userMapper.selectByExample(example).toString();
    }

    @Override
    public boolean validateLoginByName(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username).andPasswordEqualTo(password);
        if(userMapper.selectByExample(example).size()>0)
            return true;
        else
            return false;
    }

    @Override
    public boolean register(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);
        if(userMapper.selectByExample(example).size()>0)
            return false;
        else{
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            int f = userMapper.insert(user);
            return f>0;
        }
    }

    @Override
    public User briefInfo(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if(users.size()>0)
            return users.get(0);
        else{
            return null;
        }
    }

    @Override
    public boolean changeIntroduction(Integer id, String introduction) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setIntroduction(introduction);
        return userMapper.updateByPrimaryKey(user)>0;
    }

    @Override
    public User briefInfoById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
