package com.runningmate.server.domain.news.controller;

import com.runningmate.server.domain.news.service.NewsService;
import com.runningmate.server.global.common.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class NewsController {

    @GetMapping("/news")
    public BaseResponse<Object> getNews() {
        List<Map<String, String>> news = NewsService.scrapeNews();
        return new BaseResponse<>(news);
    }
}
