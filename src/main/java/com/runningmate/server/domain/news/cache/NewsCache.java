package com.runningmate.server.domain.news.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.news.model.NewsItem;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class NewsCache {

    private final AtomicReference<List<NewsItem>> cache = new AtomicReference<>(Collections.emptyList());
//    private final ObjectMapper mapper = new ObjectMapper();
//    private final Path jsonPath = Path.of("/app/data/news_data.json");

    public List<NewsItem> getCache() {
        return cache.get();
    }

    public void setCache(List<NewsItem> news) {
        cache.set(news);
    }
}
