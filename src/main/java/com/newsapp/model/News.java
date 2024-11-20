package com.newsapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private String status;
    private String totalResults;
    private List<Article> articles;
}
