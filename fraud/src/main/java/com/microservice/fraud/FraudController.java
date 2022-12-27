package com.microservice.fraud;

import com.microservices.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public record FraudController(FraudCheckHistoryService service) {

    @GetMapping(path = "{customerId}")

    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean fraudulentCustomer = service.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer {}",customerId);
        return new FraudCheckResponse(fraudulentCustomer);
    }
}
