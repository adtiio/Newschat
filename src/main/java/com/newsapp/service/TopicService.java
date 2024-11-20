package com.newsapp.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsapp.model.Comment;
import com.newsapp.model.Topic;
import com.newsapp.model.User;
import com.newsapp.repository.CommentRepository;
import com.newsapp.repository.TopicRepository;
import com.newsapp.repository.UserRepository;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    public Topic createTopic(Long userId, Topic topic){
        User user=userRepository.findById(userId).get();
        topic.setUser(user);
        Topic savedTopic=topicRepository.save(topic);

        
        user.getTopics().add(savedTopic);
        userRepository.save(user);
        return topic;
    }

    public Set<Topic> getTopics(Long userId){
        User user=userRepository.findById(userId).get();
        return user.getTopics();
    }

    public Comment addComment(Long userId, Long topicId,Comment comment){
        Topic topic=topicRepository.findById(topicId).get();
        User user=userRepository.findById(userId).get();

        comment.setTopic(topic);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        topic.getComments().add(savedComment);
        user.getComments().add(comment);
        topicRepository.save(topic);
        userRepository.save(user);
        return savedComment;
    }
    public List<Topic> topicList(Long userId){
       List<Topic> topics = new ArrayList<>();
        User user=userRepository.findById(userId).get();
        Set<User> following = user.getFollowing();
        for(User trav : following){
            topics.addAll(new ArrayList<>(trav.getTopics()));
        }
        return topics;
    }
    public Set<Comment> getCommentsByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        for(Comment comment : topic.getComments()){
            System.out.print(comment.getText()+" ");
        }
        return topic.getComments(); 
    }
 
}
