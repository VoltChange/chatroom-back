package com.lvpaul.chatroom.service;

import com.lvpaul.chatroom.entity.User;

import java.util.List;

public interface UserService {
    public String test();
    public boolean validateLoginByName(String username,String password);
    public boolean register(String username,String password);
    public User briefInfo(String username);
    public boolean changeIntroduction(Integer id,String introduction);
}
