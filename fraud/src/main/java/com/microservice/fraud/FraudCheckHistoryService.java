package com.microservice.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckHistoryService(FraudCheckHistoryRepository checkHistoryRepository) {


    public boolean isFraudulentCustomer(Integer customer_id){
        checkHistoryRepository.save((FraudCheckHistory.builder()
                .customerId(customer_id)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build()));
        return false;
    }



}
