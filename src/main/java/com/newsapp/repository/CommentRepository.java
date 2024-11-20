package com.newsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsapp.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{


}
