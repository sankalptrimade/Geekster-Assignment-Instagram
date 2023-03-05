package com.example.Instagram.service;

import com.example.Instagram.dao.UserRepository;
import com.example.Instagram.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int saveUser(User user) {
        User userObj = userRepository.save(user);
        return userObj.getUserId();
    }

    public JSONArray getUser(String userId) {
        JSONArray jsonArray = new JSONArray();
        if(null != userId){
            User user = userRepository.findById(Integer.parseInt(userId)).get();
            JSONObject jsonObject = setUser(user);
            jsonArray.put(jsonObject);
        }else{
            List<User> userList = userRepository.findAll();
            for(User user : userList){
                JSONObject jsonObject = setUser(user);
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    public JSONObject setUser(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getUserId());
        jsonObject.put("firstName", user.getFirstName());
        jsonObject.put("lastName", user.getLastName());
        jsonObject.put("age", user.getAge());
        jsonObject.put("phoneNumber", user.getPhoneNumber());
        jsonObject.put("email", user.getEmail());
        return jsonObject;
    }


    public void updateUser(String userId, User newUser) {
        if(userRepository.findById(Integer.parseInt(userId)).isPresent()){
            User user = userRepository.findById(Integer.parseInt(userId)).get();
            newUser.setUserId(user.getUserId());
            userRepository.save(newUser);
        }
    }
}
