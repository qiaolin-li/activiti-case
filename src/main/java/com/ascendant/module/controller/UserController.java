package com.ascendant.module.controller;

import com.ascendant.common.web.Result;
import com.ascendant.module.entity.User;
import com.ascendant.module.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiaolin
 * @version 2018/10/24
 **/

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("data")
    public PageInfo data(User user){
        return userService.findPage(user);
    }

    @GetMapping("{id}")
    public User get(@PathVariable String id){
        return userService.findById(id);
    }

    @PutMapping("save")
    public Result save(User user){
        userService.save(user);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id){
        userService.delete(id);
        return Result.ok("删除成功");
    }


}
