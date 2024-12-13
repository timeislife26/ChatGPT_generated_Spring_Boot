package com.example.chatgpt_project.repositories;

import com.example.chatgpt_project.entities.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {

    // Find Household by Eircode with Pets eagerly loaded
    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets WHERE h.eircode = :eircode")
    Optional<Household> findByEircodeWithPets(String eircode);

    // Find all households with no pets
    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    // Find Households that are owner-occupied
    @Query("SELECT h FROM Household h WHERE h.ownerOccupied = true")
    List<Household> findHouseholdsWithOwnerOccupied();

    // Count households with no occupants
    long countByNumberOfOccupants(int numberOfOccupants);

    // Count households where number of occupants equals max occupancy
    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    long countFullHouseholds();

    // Optional for null or non-existing eircode
    Optional<Household> findById(String eircode); // Assuming eircode is the primary key
}
