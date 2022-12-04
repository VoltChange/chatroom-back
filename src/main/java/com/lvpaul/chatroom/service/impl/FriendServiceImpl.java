package com.lvpaul.chatroom.service.impl;

import com.lvpaul.chatroom.entity.Friend;
import com.lvpaul.chatroom.entity.FriendExample;
import com.lvpaul.chatroom.entity.User;
import com.lvpaul.chatroom.entity.UserExample;
import com.lvpaul.chatroom.mapper.FriendMapper;
import com.lvpaul.chatroom.mapper.UserMapper;
import com.lvpaul.chatroom.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> friendListById(Integer id) {
        List<User> friendList = new ArrayList<>();
        //获取所有好友的id
        FriendExample friendExample = new FriendExample();
        FriendExample.Criteria friendCriteria = friendExample.createCriteria();
        friendCriteria.andUserIdEqualTo(id);
        List<Integer> friendIdList = friendMapper.selectByExample(friendExample).stream().map(Friend::getFriendId).collect(Collectors.toList());
        if(friendIdList.size()==0)
            return friendList;
        //获取好友详细信息
        UserExample userExample = new UserExample();
        UserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andIdIn(friendIdList);
        friendList = userMapper.selectByExample(userExample);
        return friendList;
    }
    @Override
    public List<User> strangerListById(Integer id) {
        //获取所有好友的id
        FriendExample friendExample = new FriendExample();
        FriendExample.Criteria friendCriteria = friendExample.createCriteria();
        friendCriteria.andUserIdEqualTo(id);
        List<Integer> friendIdList = friendMapper.selectByExample(friendExample).stream().map(Friend::getFriendId).collect(Collectors.toList());
        //加入自己
        friendIdList.add(id);
        //获取陌生人详细信息
        UserExample userExample = new UserExample();
        UserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andIdNotIn(friendIdList);
        List<User> strangerList = userMapper.selectByExample(userExample);
        return strangerList;
    }

    @Override
    @Transactional
    public boolean becomeFriend(Integer userId, Integer friendId) {
        Friend friend_A = new Friend();
        friend_A.setUserId(userId);
        friend_A.setFriendId(friendId);
        Friend friend_B = new Friend();
        friend_B.setUserId(friendId);
        friend_B.setFriendId(userId);
        return friendMapper.insert(friend_A)>0&&friendMapper.insert(friend_B)>0;
    }
    @Override
    @Transactional
    public boolean cancelFriend(Integer userId, Integer friendId) {
        FriendExample friendExample_A = new FriendExample();
        FriendExample.Criteria friendCriteria_A = friendExample_A.createCriteria();
        friendCriteria_A.andUserIdEqualTo(userId).andFriendIdEqualTo(friendId);
        FriendExample friendExample_B = new FriendExample();
        FriendExample.Criteria friendCriteria_B = friendExample_B.createCriteria();
        friendCriteria_B.andUserIdEqualTo(friendId).andFriendIdEqualTo(userId);
        return friendMapper.deleteByExample(friendExample_A)>0&&friendMapper.deleteByExample(friendExample_B)>0;
    }
}
