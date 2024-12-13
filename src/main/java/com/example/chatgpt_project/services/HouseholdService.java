package com.example.chatgpt_project.services;

import com.example.chatgpt_project.entities.Household;
import com.example.chatgpt_project.repositories.HouseholdRepository;
import com.example.chatgpt_project.repositories.PetRepository;
import com.example.chatgpt_project.graphql.dtos.HouseholdStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private PetRepository petRepository;

    // Create Household
    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    // Read All Households
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    // Read Household by ID - No Pets
    public Household getHouseholdById(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
    }

    // Read Household by ID - With Pets (Eagerly Fetch Pets)
    public Household getHouseholdWithPets(String eircode) {
        return householdRepository.findByEircodeWithPets(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
    }

    // Update Household Details
    public Household updateHousehold(String eircode, Household household) {
        Household existingHousehold = householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        existingHousehold.setNumberOfOccupants(household.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(household.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(household.isOwnerOccupied());

        // Validation: Ensure occupants don't exceed maximum
        if (existingHousehold.getNumberOfOccupants() > existingHousehold.getMaxNumberOfOccupants()) {
            throw new IllegalArgumentException("Number of occupants cannot exceed max occupancy");
        }

        return householdRepository.save(existingHousehold);
    }

    // Delete Household by ID (also delete pets)
    public void deleteHousehold(String eircode) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        // Delete all pets associated with this household
        petRepository.deleteByHouseholdId(eircode);

        // Delete the household
        householdRepository.delete(household);
    }

    // Delete Pets by Name (ignoring case)
    public void deletePetsByName(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    // Find Households with No Pets
    public List<Household> getHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    // Find Households that are Owner-Occupied
    public List<Household> findHouseholdsWithOwnerOccupied() {
        return householdRepository.findHouseholdsWithOwnerOccupied();
    }


    // Get Household Statistics (empty houses, full houses)
    public HouseholdStatistics getHouseholdStatistics() {
        long emptyHouseholds = householdRepository.countByNumberOfOccupants(0);
        long fullHouseholds = householdRepository.countFullHouseholds();

        return new HouseholdStatistics(emptyHouseholds, fullHouseholds);
    }
    // Retrieve a Household by Eircode
    public Optional<Household> getHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode); // Ensure this method is available in the repository
    }
}
