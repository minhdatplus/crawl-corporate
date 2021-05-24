package com.example.crawlssi.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface SubCrawlSsiService {

    CompletableFuture<Void> crawlCorporateAction();

}
