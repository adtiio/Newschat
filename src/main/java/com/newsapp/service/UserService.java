package com.newsapp.service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsapp.model.User;
import com.newsapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> createMultipleUsers(List<User> users){
        for(User user : users){
            userRepository.save(user);
        }
        return userRepository.findAll();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User follow(Long userId, String toFollowName) throws Exception{

        User toFollow=userRepository.findByUsername(toFollowName);
        User user = userRepository.findById(userId).get();

        if(toFollow==null) throw new Exception("User not Found with username : "+toFollowName );

        if(!toFollow.getFollowers().contains(user)){
            toFollow.getFollowers().add(user);
            user.getFollowing().add(toFollow);
            userRepository.save(user);
            userRepository.save(toFollow);
        }else throw new Exception("Already followed");

        return user;
    }

    public Set<User> getFollowing(Long userId){
        User user=userRepository.findById(userId).get();
        Set<User> following=user.getFollowing();
        return following;
    }

    public Long signup(User user){
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public Long signin(User user) throws Exception{
        String username=user.getUsername();
        String password=user.getPassword();
        User foundUser=userRepository.findByUsername(username);
        if(foundUser==null) throw new Exception("User not found");
        if(!foundUser.getPassword().equals(password))  throw new Exception("Wrong password");

        return foundUser.getId();
    }

    


}
