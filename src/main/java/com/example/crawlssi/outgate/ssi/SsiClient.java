package com.example.crawlssi.outgate.ssi;

import com.example.crawlssi.outgate.ssi.request.*;
import com.example.crawlssi.outgate.ssi.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ssiClient", url = "${url.ssi.host}")
public interface SsiClient {

    @GetMapping("${url.ssi.endpoint.defaultallstock}")
    ListLikeResp<SingleStockModel> getAllStock();

    @PostMapping("${url.ssi.endpoint.corporateactions}")
    CorporateActionResp getCorporateAction(
            @RequestBody CorporateActionReqModel corporateActionReqModel
    );

}
