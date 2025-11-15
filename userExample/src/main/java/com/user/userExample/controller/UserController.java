package com.user.userExample.controller;

import com.user.userExample.controller.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "test success";
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        user.setUsername("name chnaged to "+user.getUsername());
        return user;
    }
}
