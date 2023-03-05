package com.example.Instagram.controller;

import com.example.Instagram.dao.UserRepository;
import com.example.Instagram.model.Post;
import com.example.Instagram.model.User;
import com.example.Instagram.service.PostService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping(value = "api/v1/instagram")
public class PostController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostService postService;

    @PostMapping(value = "create-post")
    public ResponseEntity<String> savePost(@RequestBody String postRequest) {
        Post post = setPost(postRequest);
        int postId = postService.savePost(post);
        return new ResponseEntity<>("Posted with id: " + postId, HttpStatus.CREATED);
    }

    @GetMapping(value = "get-post")
    public ResponseEntity<String> getPost(@Nullable @RequestParam String postId) {
        JSONArray postArr = postService.getUser(postId);
        return new ResponseEntity<>(postArr.toString(), HttpStatus.OK);
    }

    @PutMapping(value = "update-post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String postId, @RequestBody String postRequest) {
        Post post = setPost(postRequest);
        postService.updatePost(postId, post);
        return new ResponseEntity<>("Post updated", HttpStatus.OK);
    }

    private Post setPost(String postRequest) {
        JSONObject jsonObject = new JSONObject(postRequest);
        User user = null;
        int userId = jsonObject.getInt("userId");
        if (userRepository.findById(userId).isPresent()) {
            user = userRepository.findById(userId).get();
        } else {
            return null;
        }
        Post post = new Post();
        post.setUser(user);
        post.setPostData(jsonObject.getString("postData"));
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        post.setCreatedDate(createdTime);
        return post;
    }
}
