package com.microservices.customer;

import com.microservices.amqp.RabbitMQMessageProducer;
import com.microservices.clients.fraud.FraudCheckResponse;
import com.microservices.clients.fraud.FraudClient;
import com.microservices.clients.notification.NotificationClient;
import com.microservices.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository /*RestTemplate restTemplate*/,
                              FraudClient fraudClient, NotificationClient notificationClient, RabbitMQMessageProducer producer) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

      /* FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(       ДЛЯ НЕТФЛИКС
                "http://FRAUD:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class, customer.getId());
        }*/

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());  //    ОПЕНФЕЙН

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(), String.format("Hi %s, welcome!", customer.getFirstName()));
        producer.publish(notificationRequest,"internal.exchange","internal.notification.routing-key");
    }
}
