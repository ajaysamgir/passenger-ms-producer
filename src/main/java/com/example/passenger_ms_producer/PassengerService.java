package com.example.passenger_ms_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PassengerService {

    @Autowired
    private KafkaTemplate<String, Passenger> kafkaTemplate;


    public ResponseEntity sendEvent(Passenger passenger) {
        CompletableFuture<SendResult<String, Passenger>> result = this.kafkaTemplate.send("passenger", passenger);
        AtomicReference<ResponseEntity<Passenger>> responseEntity = new AtomicReference<>(new ResponseEntity<>(HttpStatus.OK));
        result.whenComplete((stringPassengerSendResult, throwable) -> {
            if (throwable != null) {
                responseEntity.set(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            } else {
                responseEntity.set(new ResponseEntity<>(passenger, HttpStatus.OK));
            }
        });
        return responseEntity.get();
    }
}
