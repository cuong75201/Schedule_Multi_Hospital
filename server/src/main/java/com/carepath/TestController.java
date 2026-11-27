package com.carepath;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carepath.contracts.IBillingFeeService;
import com.carepath.contracts.IBillingFeeService.FeeTierConfig;

@RestController
@RequestMapping("/api/users")
public class TestController {

    private IBillingFeeService billingFeeService;

    TestController(IBillingFeeService billingFeeService) {
        this.billingFeeService = billingFeeService;
    }


    record TestRequest(String code, double minRevenue, double maxRevenue, double feeRatePct,
            String effectiveFrom, String effectiveTo, String createdById) {
    }

    @PostMapping
    public FeeTierConfig test(@RequestBody TestRequest request) {
        FeeTierConfig fee = billingFeeService.upsertFeeTierConfig(
                request.code(),
                request.minRevenue(),
                request.maxRevenue(),
                request.feeRatePct(),
                request.effectiveFrom(),
                request.effectiveTo(),
                request.createdById());
        return fee;

    }
    
}
