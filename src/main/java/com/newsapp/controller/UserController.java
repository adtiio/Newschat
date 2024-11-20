package com.newsapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.newsapp.model.User;
import com.newsapp.repository.UserRepository;
import com.newsapp.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public User create(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("/createMultiple")
    public List<User> createMultiple(@RequestBody List<User> users){
        System.out.println(users);
        return userService.createMultipleUsers(users);
    }

    @GetMapping("/get/{userId}")
    public User getUser(@PathVariable Long userId){
        return userRepository.findById(userId).get();
    }

    @GetMapping("/getUser")
    public User getByName(@RequestBody String username){
        return userService.getUser(username);
    }
    
    @PostMapping("/getByStart")
    public List<User> getByPrefix(@RequestBody String username){
        System.out.println(username);
        List<User> users= userRepository.findByUsernameStartingWith(username);
        for(User u : users){
            System.out.print(u.getUsername()+ " "+" "+users.size());
        }
        System.out.println(users.size());
        return users;
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping("/follow/{userId}")
    public User followUser(@PathVariable Long userId,@RequestBody User toFollow) throws Exception{
        String username=toFollow.getUsername();
        System.out.println(username);
        if(username==null) throw new Exception("Please enter username");
        return userService.follow(userId, username);
    }

    @GetMapping("/getConnection/{id}")
    public HashMap<String,Set<User>> getConnection(@PathVariable Long id){
        User user=userRepository.findById(id).get();
        HashMap<String,Set<User>> map=new HashMap<>();
        map.put("followers",user.getFollowers());
        map.put("following", user.getFollowing());
        return map;
    }

    @PostMapping("/signup")
    public RedirectView signup(User user){
        Long userId= userService.signup(user);
        return new RedirectView("/home/" + userId); 
    }

    @PostMapping("/signin")
    public RedirectView signin(User user) throws Exception{
        Long userId= userService.signin(user);
        return new RedirectView("/home/" + userId);
    }

    @GetMapping("/getFollowing/{userId}")
    public Set<User> getFollowing(@PathVariable Long userId){
        return userService.getFollowing(userId);
    }
    

}
