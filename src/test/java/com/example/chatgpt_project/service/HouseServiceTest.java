package com.example.chatgpt_project.service;

import com.example.chatgpt_project.entities.Household;
import com.example.chatgpt_project.repositories.HouseholdRepository;
import com.example.chatgpt_project.repositories.PetRepository;
import com.example.chatgpt_project.graphql.dtos.HouseholdStatistics;
import com.example.chatgpt_project.services.HouseholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseholdServiceTest {

    @Mock
    private HouseholdRepository householdRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private HouseholdService householdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHousehold() {
        Household household = new Household();
        household.setEircode("E123");

        when(householdRepository.save(household)).thenReturn(household);

        Household createdHousehold = householdService.createHousehold(household);

        assertNotNull(createdHousehold);
        assertEquals("E123", createdHousehold.getEircode());
        verify(householdRepository, times(1)).save(household);
    }

    @Test
    void testGetAllHouseholds() {
        Household h1 = new Household();
        h1.setEircode("E123");
        Household h2 = new Household();
        h2.setEircode("E124");

        when(householdRepository.findAll()).thenReturn(Arrays.asList(h1, h2));

        List<Household> households = householdService.getAllHouseholds();

        assertEquals(2, households.size());
        assertEquals("E123", households.get(0).getEircode());
        assertEquals("E124", households.get(1).getEircode());
        verify(householdRepository, times(1)).findAll();
    }

    @Test
    void testGetHouseholdByIdExists() {
        Household household = new Household();
        household.setEircode("E123");

        when(householdRepository.findById("E123")).thenReturn(Optional.of(household));

        Household foundHousehold = householdService.getHouseholdById("E123");

        assertNotNull(foundHousehold);
        assertEquals("E123", foundHousehold.getEircode());
        verify(householdRepository, times(1)).findById("E123");
    }

    @Test
    void testGetHouseholdByIdNotFound() {
        when(householdRepository.findById("E123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> householdService.getHouseholdById("E123"));

        assertEquals("Household not found", exception.getMessage());
        verify(householdRepository, times(1)).findById("E123");
    }

    @Test
    void testGetHouseholdWithPetsExists() {
        Household household = new Household();
        household.setEircode("E123");

        when(householdRepository.findByEircodeWithPets("E123")).thenReturn(Optional.of(household));

        Household foundHousehold = householdService.getHouseholdWithPets("E123");

        assertNotNull(foundHousehold);
        assertEquals("E123", foundHousehold.getEircode());
        verify(householdRepository, times(1)).findByEircodeWithPets("E123");
    }

    @Test
    void testGetHouseholdWithPetsNotFound() {
        when(householdRepository.findByEircodeWithPets("E123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> householdService.getHouseholdWithPets("E123"));

        assertEquals("Household not found", exception.getMessage());
        verify(householdRepository, times(1)).findByEircodeWithPets("E123");
    }

    @Test
    void testUpdateHousehold() {
        Household existingHousehold = new Household();
        existingHousehold.setEircode("E123");
        existingHousehold.setNumberOfOccupants(2);
        existingHousehold.setMaxNumberOfOccupants(4);

        Household updatedHousehold = new Household();
        updatedHousehold.setNumberOfOccupants(3);
        updatedHousehold.setMaxNumberOfOccupants(4);

        when(householdRepository.findById("E123")).thenReturn(Optional.of(existingHousehold));
        when(householdRepository.save(existingHousehold)).thenReturn(existingHousehold);

        Household result = householdService.updateHousehold("E123", updatedHousehold);

        assertNotNull(result);
        assertEquals(3, result.getNumberOfOccupants());
        assertEquals(4, result.getMaxNumberOfOccupants());
        verify(householdRepository, times(1)).findById("E123");
        verify(householdRepository, times(1)).save(existingHousehold);
    }

    @Test
    void testDeleteHousehold() {
        Household household = new Household();
        household.setEircode("E123");

        when(householdRepository.findById("E123")).thenReturn(Optional.of(household));
        doNothing().when(petRepository).deleteByHouseholdId("E123");
        doNothing().when(householdRepository).delete(household);

        householdService.deleteHousehold("E123");

        verify(petRepository, times(1)).deleteByHouseholdId("E123");
        verify(householdRepository, times(1)).delete(household);
    }

    @Test
    void testGetHouseholdsWithNoPets() {
        Household h1 = new Household();
        h1.setEircode("E123");
        Household h2 = new Household();
        h2.setEircode("E124");

        when(householdRepository.findHouseholdsWithNoPets()).thenReturn(Arrays.asList(h1, h2));

        List<Household> households = householdService.getHouseholdsWithNoPets();

        assertEquals(2, households.size());
        verify(householdRepository, times(1)).findHouseholdsWithNoPets();
    }

    @Test
    void testGetHouseholdStatistics() {
        when(householdRepository.countByNumberOfOccupants(0)).thenReturn(5L);
        when(householdRepository.countFullHouseholds()).thenReturn(3L);

        HouseholdStatistics stats = householdService.getHouseholdStatistics();

        assertNotNull(stats);
        assertEquals(5L, stats.getEmptyHouses());
        assertEquals(3L, stats.getFullHouses());
        verify(householdRepository, times(1)).countByNumberOfOccupants(0);
        verify(householdRepository, times(1)).countFullHouseholds();
    }
}
