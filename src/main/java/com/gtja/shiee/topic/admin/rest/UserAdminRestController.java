package com.gtja.shiee.topic.admin.rest;

import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.service.UserService;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/rest/u")
public class UserAdminRestController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public Result getAll(Page page) {
        return Result.success()
                .add("users", userService.getAll(page))
                .add("page", page);
    }

    @GetMapping("/{userId}")
    public Result get(@PathVariable String userId) throws ResultException {
        return Result.success().add("user", userService.get(userId));
    }

    @PutMapping("/{userId}")
    public Result save(@PathVariable String userId, User user) throws ResultException {
        return Result.success().add("user", userService.save(user));
    }

}
