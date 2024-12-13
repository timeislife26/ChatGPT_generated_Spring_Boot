package com.example.chatgpt_project.services;

import com.example.chatgpt_project.entities.Pet;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface PetService {
    Optional<Pet> findById(Long id);
    List<Pet> findAll();
    Pet save(Pet pet);
    void delete(Long id);

    List<Pet> getAllPets();

    Optional<Pet> getPetById(Long id);
    Pet createPet(Pet pet);
    Pet updatePetName(long id, String name);

    void deletePet(Long id);
    List<Pet> findPetsByAnimalType(String animalType);
}
