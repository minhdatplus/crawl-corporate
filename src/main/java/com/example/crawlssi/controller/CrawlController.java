package com.example.crawlssi.controller;


import com.example.crawlssi.factory.resfact.GeneralResponse;
import com.example.crawlssi.factory.resfact.ResponseFactory;
import com.example.crawlssi.service.SubCrawlSsiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/external/crawler/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrawlController {

    private final SubCrawlSsiService subCrawlSsiService;

    @Autowired
    public CrawlController(
            SubCrawlSsiService subCrawlSsiService
    ) {
        this.subCrawlSsiService = subCrawlSsiService;
    }

    @GetMapping("/crawl-corporateaction")
    public ResponseEntity<GeneralResponse<Object>> crawlCorporateAction() {
        subCrawlSsiService.crawlCorporateAction();
        return ResponseFactory.success();
    }

}
