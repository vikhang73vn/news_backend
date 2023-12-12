package com.erp.backend.controllers;

import com.erp.backend.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "users")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @ApiOperation(value = "lấy thông tin user")
    public ResponseEntity<?> getProfile(@RequestAttribute("email") String user) {
        return ResponseEntity.ok(userService.getProfile(user));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/follows")
    public  ResponseEntity<?> getFollowsArticle (@RequestAttribute("email") String email){
return  ResponseEntity.ok(userService.getArticleFollows(email));

    }

}
