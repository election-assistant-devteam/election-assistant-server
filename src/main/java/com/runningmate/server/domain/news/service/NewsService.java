package com.runningmate.server.domain.news.service;

import com.runningmate.server.domain.news.model.NewsItem;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@Service
public class NewsService {

    private static final String os = System.getProperty("os.name").toLowerCase();

    @PostConstruct
    public void initDriver() {
        if (os.contains("linux")) {
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .clearResolutionCache()
                    .setup();
        } else {
            WebDriverManager.chromedriver().setup(); // 자동 감지
        }
    }

    public static List<NewsItem> scrapeNews() {

        ChromeOptions options = new ChromeOptions();
        if (os.contains("linux")) {
            options.setBinary("/usr/bin/chromium-browser");    // 컨테이너에서만 필요
        }
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
        List<NewsItem> newsList = new ArrayList<>();

        try {
            driver.get("https://news.naver.com/section/100");

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("#newsct > div.section_component.as_section_headline._PERSIST_CONTENT > div.section_article.as_headline._TEMPLATE")
                    ));

            List<WebElement> links = driver.findElements(By.cssSelector(".sa_text > a"));
            List<WebElement> titles = driver.findElements(By.cssSelector(".sa_text_strong"));
            List<WebElement> imgs = driver.findElements(By.cssSelector(".section_article img"));

            for (int i = 0; i < titles.size(); i++) {
                newsList.add(new NewsItem(
                        titles.get(i).getText(),
                        links.get(i).getAttribute("href"),
                        imgs.get(i).getAttribute("src")));
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
