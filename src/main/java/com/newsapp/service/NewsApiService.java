package com.newsapp.service;

import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.newsapp.model.News;

@Service
public class NewsApiService {

    String baseUrl="https://newsapi.org/v2/everything";
    String apiKey="API_KEY";

    private final RestTemplate restTemplate;

    public NewsApiService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }

    public News getResponse(){
        String url=baseUrl+"?q=india&from=2024-10-19&language=en&sortBy=publishedAt&apiKey="+apiKey;
    
        News response = restTemplate.getForObject(url, News.class);
        return response;
    }
}
