package com.example.crawlssi.schedulers;

import com.example.crawlssi.service.SubCrawlSsiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class CorporateCrawlScheduler {

    private final SubCrawlSsiService subCrawlSsiService;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    public CorporateCrawlScheduler(
            SubCrawlSsiService subCrawlSsiService
    ) {
        this.subCrawlSsiService = subCrawlSsiService;
    }

    @Scheduled(cron = "${crawl.cron}")
    public void crawlEach12Hour() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Start crawl news at {} ", dtf.format(now));
        subCrawlSsiService.crawlCorporateAction().join();
        log.info("Done crawl news at {} ", dtf.format(now));
    }

}
