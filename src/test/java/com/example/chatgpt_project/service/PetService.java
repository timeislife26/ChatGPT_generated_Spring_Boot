package com.example.chatgpt_project.service;

import com.example.chatgpt_project.entities.Pet;
import com.example.chatgpt_project.repositories.PetRepository;
import com.example.chatgpt_project.services.PetServiceImpl;
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

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePet() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setAnimalType("Dog");

        when(petRepository.save(pet)).thenReturn(pet);

        Pet savedPet = petService.save(pet);

        assertNotNull(savedPet);
        assertEquals("Buddy", savedPet.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testFindByIdPetExists() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> foundPet = petService.findById(1L);

        assertTrue(foundPet.isPresent());
        assertEquals("Buddy", foundPet.get().getName());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdPetNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> petService.findById(1L));

        assertEquals("Pet not found with id: 1", exception.getMessage());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllPets() {
        Pet pet1 = new Pet();
        pet1.setName("Buddy");
        Pet pet2 = new Pet();
        pet2.setName("Charlie");

        when(petRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<Pet> pets = petService.findAll();

        assertEquals(2, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
        assertEquals("Charlie", pets.get(1).getName());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void testDeletePet() {
        when(petRepository.existsById(1L)).thenReturn(true);
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        verify(petRepository, times(1)).existsById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePetNotFound() {
        when(petRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> petService.deletePet(1L));

        assertEquals("Pet not found with id 1", exception.getMessage());
        verify(petRepository, times(1)).existsById(1L);
    }

    @Test
    void testFindPetsByAnimalType() {
        Pet pet1 = new Pet();
        pet1.setName("Buddy");
        pet1.setAnimalType("Dog");

        when(petRepository.findByAnimalTypeIgnoreCase("Dog")).thenReturn(Arrays.asList(pet1));

        List<Pet> pets = petService.findPetsByAnimalType("Dog");

        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
        verify(petRepository, times(1)).findByAnimalTypeIgnoreCase("Dog");
    }

    @Test
    void testUpdatePetName() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet updatedPet = petService.updatePetName(1L, "Max");

        assertNotNull(updatedPet);
        assertEquals("Max", updatedPet.getName());
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(any(Pet.class));
    }
}