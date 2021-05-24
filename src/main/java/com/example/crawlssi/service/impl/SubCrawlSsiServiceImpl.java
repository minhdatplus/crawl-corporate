package com.example.crawlssi.service.impl;

import com.example.crawlssi.outgate.ssi.SsiClient;
import com.example.crawlssi.outgate.ssi.request.CorporateActionReqModel;
import com.example.crawlssi.outgate.ssi.response.*;
import com.example.crawlssi.repository.CorporateActionRepository;
import com.example.crawlssi.repository.entity.CorporateAction;
import com.example.crawlssi.service.SubCrawlSsiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SubCrawlSsiServiceImpl implements SubCrawlSsiService {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private final SsiClient ssiClient;
    private final CorporateActionRepository corporateActionRepository;

    private final List<String> dateTypeList;
    private final List<String> eventCodeList;

    @Autowired
    public SubCrawlSsiServiceImpl(
            SsiClient ssiClient,
            CorporateActionRepository corporateActionRepository
    ) {
        this.ssiClient = ssiClient;
        this.corporateActionRepository = corporateActionRepository;

        this.dateTypeList = Arrays.asList("ex_date", "pub_date");
        this.eventCodeList = Arrays.asList("dhcd", "ny", "gdnb", "tct", "kqkd", "skk");
    }

    private void saveCorporateActionToDb(CorporateActionModel corporateActionModel) {
        CorporateAction corporateAction = new CorporateAction();
        BeanUtils.copyProperties(corporateActionModel, corporateAction);

        try {
            corporateActionRepository.save(corporateAction);
        } catch (Exception e) {
            log.info("Failed to save corporateActionModel to Db {} --> {} - {}", corporateAction, e.getMessage(), e.getCause());
        }
    }


    private void crawlAndSaveCorporateAction(String symbol) {
        CorporateActionReqModel corporateActionReqModel = new CorporateActionReqModel();
        corporateActionReqModel.setSymbol(symbol);
        corporateActionReqModel.setOffset(1);
        corporateActionReqModel.setSize(1000000000);
        corporateActionReqModel.setFromDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        corporateActionReqModel.setToDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));

        for (String dateType : dateTypeList)
            for (String eventCode : eventCodeList) {
                corporateActionReqModel.setEventCode(eventCode);
                corporateActionReqModel.setDateType(dateType);
                try {
                    CorporateActionResp corporateActionResp = ssiClient.getCorporateAction(corporateActionReqModel);
                    corporateActionResp.getData().getCorporateActions().getDataList().forEach(this::saveCorporateActionToDb);
                } catch (Exception e) {
                    log.info("Failed to crawl corporate action --> {} - {}", e.getMessage(), e.getCause());
                }
            }

    }

    @Override
    public CompletableFuture<Void> crawlCorporateAction() {
        return CompletableFuture.runAsync(() -> {
            List<SingleStockModel> listSingleStock = ssiClient.getAllStock().getData();
            listSingleStock.forEach(item -> crawlAndSaveCorporateAction(item.getCode()));
        });
    }
}
