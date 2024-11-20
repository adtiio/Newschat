package com.newsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsapp.model.Topic;

public interface TopicRepository extends JpaRepository<Topic,Long>{

}
