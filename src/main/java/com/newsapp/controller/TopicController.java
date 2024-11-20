package com.newsapp.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newsapp.model.Comment;
import com.newsapp.model.Topic;
import com.newsapp.service.TopicService;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;


    @PostMapping("topic/{userId}")
    public Topic createTopic(@PathVariable Long userId, @RequestBody Topic topic){
        return topicService.createTopic(userId, topic);
    }

    @GetMapping("/getTopic/{userId}")
    public Set<Topic> getTopics(@PathVariable Long userId){
        return topicService.getTopics(userId);
    }

    @GetMapping("/topics/{userId}")
    public List<Topic> getFollowedTopics(@PathVariable Long userId){
        return topicService.topicList(userId);
    }

    @PostMapping("/addComment/{userId}/{topicId}")
    public Comment addComment(@PathVariable Long userId, @PathVariable Long topicId, @RequestBody Comment comment){
        System.out.println("userId: "+userId);
        System.out.println("topicId: "+topicId);
        return topicService.addComment(userId,topicId, comment);
    }

    @GetMapping("/comments/{topicId}")
    public Set<Comment> getComments(@PathVariable Long topicId) {
        return topicService.getCommentsByTopicId(topicId);
    }
}
