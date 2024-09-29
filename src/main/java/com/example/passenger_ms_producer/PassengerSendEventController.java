package com.example.passenger_ms_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/passenger")
public class PassengerSendEventController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping(value = "/add",consumes = "application/json", produces = "application/json")
    public ResponseEntity addPassenger(@RequestBody Passenger passenger) {
        return passengerService.sendEvent(passenger);
    }
}
