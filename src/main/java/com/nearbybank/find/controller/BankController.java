package com.nearbybank.find.controller;

import com.nearbybank.find.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<?> getNearbyBanks(@RequestParam("zipcode") String zipcode) {
        return bankService.findNearbyBanks(zipcode);
    }
}
