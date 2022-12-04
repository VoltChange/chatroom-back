package com.lvpaul.chatroom.controller;

import com.lvpaul.chatroom.entity.Friend;
import com.lvpaul.chatroom.entity.User;
import com.lvpaul.chatroom.service.FriendService;
import com.lvpaul.chatroom.service.UserService;
import com.lvpaul.chatroom.vo.NameLoginVo;
import com.lvpaul.chatroom.vo.RegistryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/friend")
public class FriendController {
    @Autowired
    FriendService friendService;
    @ApiOperation("返回好友列表")
    @GetMapping("friendList")
    public ResponseEntity friendList(@RequestParam Integer userId){
        List<User> friendList = friendService.friendListById(userId);
        return ResponseEntity.ok().body(friendList);
    }
    @ApiOperation("返回陌生人列表")
    @GetMapping("strangerList")
    public ResponseEntity strangerList(@RequestParam Integer userId){
        List<User> strangerList = friendService.strangerListById(userId);
        return ResponseEntity.ok().body(strangerList);
    }
    @ApiOperation("成为好友")
    @GetMapping("become")
    public ResponseEntity become(@RequestParam Integer userId,@RequestParam Integer friendId){
        if(friendService.becomeFriend(userId, friendId))
            return ResponseEntity.ok().body("成功");
        else
            return ResponseEntity.status(201).body("失败");
    }
    @ApiOperation("移除好友")
    @GetMapping("cancel")
    public ResponseEntity cancel(@RequestParam Integer userId,@RequestParam Integer friendId){
        if(friendService.cancelFriend(userId, friendId))
            return ResponseEntity.ok().body("成功");
        else
            return ResponseEntity.status(201).body("失败");
    }
}
