package com.runningmate.server.domain.news.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    public static List<Map<String, String>> scrapeNews() {

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/chromium-browser");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        ChromeDriverService service = new ChromeDriverService.Builder()
                .withLogOutput(System.err)      // STDERR 로 바로 출력
                .withVerbose(true)               // 상세 로그
                .build();

        WebDriver driver = new ChromeDriver(service, options);
        List<Map<String, String>> newsList = new ArrayList<>();

        try {
            driver.get("https://news.naver.com/section/100");

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("#newsct > div.section_component.as_section_headline._PERSIST_CONTENT > div.section_article.as_headline._TEMPLATE")
                    ));

            List<WebElement> links = driver.findElements(By.cssSelector(".sa_text > a"));
            List<WebElement> headlines = driver.findElements(By.cssSelector(".sa_text_strong"));
            List<WebElement> imgs = driver.findElements(By.cssSelector(".section_article img"));

            for (int i = 0; i < headlines.size(); i++) {
                String title = headlines.get(i).getText();
                String link = links.get(i).getAttribute("href");
                String imgUrl = imgs.get(i).getAttribute("src");

                Map<String, String> newsItem = new HashMap<>();
                newsItem.put("title", title);
                newsItem.put("link", link);
                newsItem.put("image", imgUrl);  // 로컬 저장 안하고 URL 그대로 사용
                newsList.add(newsItem);
            }

//            System.out.println("✅ news_data.json 저장 완료");

        } catch (Exception e) {
            System.err.println("스크래핑 중 오류 발생: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return newsList;
    }
}
