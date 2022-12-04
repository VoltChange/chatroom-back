package com.lvpaul.chatroom.service;

import com.lvpaul.chatroom.entity.Friend;
import com.lvpaul.chatroom.entity.User;

import java.util.List;

public interface FriendService {
    public List<User> friendListById(Integer id);
    public List<User> strangerListById(Integer id);
    public boolean becomeFriend(Integer userId,Integer friendId);
    public boolean cancelFriend(Integer userId,Integer friendId);
}
