package com.example.chatgpt_project.repositories;

import com.example.chatgpt_project.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("DELETE FROM Pet p WHERE LOWER(p.name) = LOWER(:name)")
    void deleteByNameIgnoreCase(String name);

    @Query("SELECT p FROM Pet p WHERE LOWER(p.animalType) = LOWER(:animalType)")
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);

    @Query("SELECT p FROM Pet p WHERE LOWER(p.breed) = LOWER(:breed) ORDER BY p.age ASC")
    List<Pet> findByBreedOrderByAgeAsc(String breed);

    @Query("SELECT p.name, p.animalType, p.breed FROM Pet p")
    List<Object[]> findPetNameAndBreed();

    @Query("SELECT AVG(p.age), MAX(p.age), COUNT(p) FROM Pet p")
    Object[] getPetStatistics();

    // Custom query to delete all pets associated with a specific household (via eircode)
    @Query("DELETE FROM Pet p WHERE p.household.eircode = :eircode")
    void deleteByHouseholdId(String eircode);
}
