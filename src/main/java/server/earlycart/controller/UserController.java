package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.earlycart.service.UserService;
import server.earlycart.model.User;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("user/register")
    public String registerUser(@RequestParam String id, String pw, String name, String birth, String phone) {
        return userService.register(id, pw, name, birth, phone);
    }

    @GetMapping("user/login")
    public String loginUser(@RequestParam String id, String pw) {
        return userService.login(id, pw);
    }

    @GetMapping("user")
    public User getUserBySession(@RequestParam String session) {
        return userService.getUserBySession(session);
    }


}
