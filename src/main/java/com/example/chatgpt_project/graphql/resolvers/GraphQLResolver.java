package com.example.chatgpt_project.graphql.resolvers;

import com.example.chatgpt_project.entities.Household;
import com.example.chatgpt_project.entities.Pet;
import com.example.chatgpt_project.graphql.resolvers.*;
import com.example.chatgpt_project.graphql.dtos.HouseholdStatistics;


import com.example.chatgpt_project.services.HouseholdService;
import com.example.chatgpt_project.services.PetService;
import org.dataloader.stats.Statistics;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLResolver {

    private final HouseholdService householdService;
    private final PetService petService;

    public GraphQLResolver(HouseholdService householdService, PetService petService) {
        this.householdService = householdService;
        this.petService = petService;
    }

    @QueryMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public List<Pet> getAllPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household getHousehold(@Argument String eircode) {
        return householdService.getHouseholdByEircode(eircode)
                .orElse(null); // Return null if not found
    }

    @QueryMapping
    public Pet getPet(@Argument Long id) {
        return petService.getPetById(id)
                .orElse(null); // Return null if not found
    }

    @QueryMapping
    public HouseholdStatistics getStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    public Household createHousehold(@Argument Household input) {
        return householdService.createHousehold(input);
    }

    @MutationMapping
    public Boolean deleteHousehold(@Argument String eircode) {
        try {
            householdService.deleteHousehold(eircode);
            return true; // Indicate success
        } catch (Exception e) {
            return false; // Indicate failure
        }
    }

    @MutationMapping
    public Boolean deletePet(@Argument Long id) {
        try {
            petService.deletePet(id);
            return true; // Indicate success
        } catch (Exception e) {
            return false; // Indicate failure
        }
    }

}
