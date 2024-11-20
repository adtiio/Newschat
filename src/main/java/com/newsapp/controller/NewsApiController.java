package com.newsapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsapp.model.Article;
import com.newsapp.model.News;
import com.newsapp.service.NewsApiService;

@RestController
@RequestMapping("/news")
public class NewsApiController {

    private final NewsApiService newsApiService;

    public NewsApiController(NewsApiService newsApiService){
        this.newsApiService=newsApiService;
    }

    @GetMapping("/data")
    public News apiCall(){
        News news= newsApiService.getResponse();

        List<Article> articles= news.getArticles();
        System.out.println("______________first five_______________");
        for(int i=0;i<5;i++){
            System.out.println(articles.get(i).getTitle() +"| published at: " +articles.get(i).getPublishedAt());
            System.out.println("_______________________________________");
        }

        System.out.println("___________last five___________________");
        for(int i=articles.size()-1;i>=articles.size()-6;i--){
            System.out.println(articles.get(i).getTitle()+"| published at: "  + articles.get(i).getPublishedAt());
            System.out.println("_______________________________________");
        }

        System.out.println(articles.size());
        
        return news;
    }

}
