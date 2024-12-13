package com.example.chatgpt_project.controllers;

import com.example.chatgpt_project.entities.Household;
import com.example.chatgpt_project.services.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    @Autowired
    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/{eircode}")
    public ResponseEntity<Household> getHousehold(@PathVariable String eircode) {
        return householdService.getHouseholdByEircode(eircode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody Household household) {
        Household createdHousehold = householdService.createHousehold(household);
        return ResponseEntity.status(201).body(createdHousehold);
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }
}
