package com.example.Instagram.service;

import com.example.Instagram.dao.PostRepository;
import com.example.Instagram.dao.UserRepository;
import com.example.Instagram.model.Post;
import com.example.Instagram.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;

    public int savePost(Post post) {
        Post savePost = postRepository.save(post);
        return savePost.getPostId();
    }

    public JSONArray getUser(String postId) {
        JSONArray postArr = new JSONArray();
        if (null != postId && postRepository.findById(Integer.parseInt(postId)).isPresent()) {
            Post post = postRepository.findById(Integer.parseInt(postId)).get();
            JSONObject postObj = setPost(post);
            postArr.put(postObj);
        } else {
            List<Post> postList = postRepository.findAll();
            for(Post post : postList){
                JSONObject postObj = setPost(post);
                postArr.put(postObj);
            }
        }
        return postArr;
    }

    private JSONObject setPost(Post post) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postId", post.getPostId());
        jsonObject.put("postData", post.getPostData());

        User user = post.getUser();
        JSONObject userObj = new JSONObject();
        userObj.put("userId", user.getUserId());
        userObj.put("firstName", user.getFirstName());
        userObj.put("lastName", user.getLastName());
        userObj.put("age", user.getAge());
        userObj.put("email", user.getEmail());
        userObj.put("phoneNumber", user.getPhoneNumber());

        jsonObject.put("user", userObj);

        return jsonObject;
    }

    public void updatePost(String postId, Post newPost) {
        if(postRepository.findById(Integer.parseInt(postId)).isPresent()){
            Post post = postRepository.findById(Integer.parseInt(postId)).get();
            newPost.setPostId(post.getPostId());
            newPost.setUser(post.getUser());
            newPost.setCreatedDate(post.getCreatedDate());
            Timestamp updateDate = new Timestamp(System.currentTimeMillis());
            newPost.setUpdateDate(updateDate);
            postRepository.save(newPost);
        }
    }
}
