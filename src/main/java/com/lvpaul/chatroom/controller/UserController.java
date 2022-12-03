package com.lvpaul.chatroom.controller;

import com.lvpaul.chatroom.entity.User;
import com.lvpaul.chatroom.service.UserService;
import com.lvpaul.chatroom.vo.NameLoginVo;
import com.lvpaul.chatroom.vo.RegistryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserService userService;
    @ApiOperation(value = "向客人问好")
    @GetMapping("echo")
    public String echo(@RequestParam String context){
        return "Hello "+context+userService.test();
    }
    @ApiOperation(value = "用户名登录")
    @PostMapping("name")
    public ResponseEntity loginByName(@RequestBody NameLoginVo nameLoginVo){
        if(nameLoginVo.getUsername()==null||nameLoginVo.getUsername().equals(""))
            return ResponseEntity.status(201).body("用户名不能为空");
        if(nameLoginVo.getPassword()==null||nameLoginVo.getPassword().equals(""))
            return ResponseEntity.status(201).body("密码不能为空");
        if(userService.validateLoginByName(nameLoginVo.getUsername(),nameLoginVo.getPassword()))
            return ResponseEntity.ok().body("成功");
        else
            return ResponseEntity.status(201).body("用户名或密码错误");
    }
    @ApiOperation(value = "注册")
    @PostMapping
    public ResponseEntity register(@RequestBody RegistryVo registryVo){
        if(registryVo.getUsername()==null||registryVo.getUsername().equals(""))
            return ResponseEntity.status(201).body("用户名不能为空");
        if(registryVo.getPassword()==null||registryVo.getPassword().equals(""))
            return ResponseEntity.status(201).body("密码不能为空");
        if(userService.register(registryVo.getUsername(),registryVo.getPassword()))
            return ResponseEntity.ok().body("成功");
        else
            return ResponseEntity.status(201).body("用户名已存在");
    }
    @ApiOperation("根据用户名返回简要信息")
    @GetMapping("briefInfo")
    public ResponseEntity briefInfo(@RequestParam String username){
        User user = userService.briefInfo(username);
        return ResponseEntity.ok().body(user);
    }
    @ApiOperation("修改个人简介")
    @GetMapping("changeIntroduction")
    public ResponseEntity changeIntroduction(@RequestParam Integer id,@RequestParam String introduction){
        if(userService.changeIntroduction(id,introduction))
            return ResponseEntity.ok().body("成功");
        else
            return ResponseEntity.status(201).body("失败");
    }
}
