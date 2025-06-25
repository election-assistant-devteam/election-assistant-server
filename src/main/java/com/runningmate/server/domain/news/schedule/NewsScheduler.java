package com.runningmate.server.domain.news.schedule;

import com.runningmate.server.domain.news.cache.NewsCache;
import com.runningmate.server.domain.news.service.NewsService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsScheduler {

    private final NewsService newsService;
    private final NewsCache cache;

    public NewsScheduler(NewsService newsService, NewsCache cache) {
        this.newsService = newsService;
        this.cache = cache;
    }

    @PostConstruct
    public void firstLoad() {
        refresh();
    }

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void refresh() {
        try {
            cache.setCache(newsService.scrapeNews());
            System.out.println("뉴스 캐시 갱신 완료");
        } catch (Exception e) {
            System.err.println("뉴스 캐시 갱신 중 오류 발생: " + e.getMessage());
        }
    }
}
