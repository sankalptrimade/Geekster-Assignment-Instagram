package com.example.Instagram.controller;

import com.example.Instagram.model.User;
import com.example.Instagram.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/instagram")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "create-user")
    public ResponseEntity<String> saveUser(@RequestBody String userData){
        User user = setUser(userData);
        int userId = userService.saveUser(user);
        return new ResponseEntity<String>("User save with id "+userId, HttpStatus.CREATED);
    }
    private User setUser(String userData) {
        JSONObject jsonObject = new JSONObject(userData);
        User user = new User();
        user.setFirstName(jsonObject.getString("firstName"));
        user.setLastName(jsonObject.getString("lastName"));
        user.setAge(jsonObject.getInt("age"));
        user.setEmail(jsonObject.getString("email"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));
        return user;
    }
    @GetMapping(value = "get-user")
    public ResponseEntity<String> getUser(@Nullable @RequestParam String userId){
        JSONArray userDetails = userService.getUser(userId);
        return new ResponseEntity<>(userDetails.toString(), HttpStatus.OK);
    }
    @PutMapping(value = "update-user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody String userData){
        User user = setUser(userData);
        userService.updateUser(userId, user);
        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }

}
