package com.runningmate.server.domain.news.controller;

import com.runningmate.server.domain.news.cache.NewsCache;
import com.runningmate.server.domain.news.service.NewsService;
import com.runningmate.server.global.common.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class NewsController {

    private final NewsCache cache;

    public NewsController(NewsCache cache) {
        this.cache = cache;
    }

    @GetMapping("/news")
    public BaseResponse<Object> getNews() {
        return new BaseResponse<>(cache.getCache());
    }
}
