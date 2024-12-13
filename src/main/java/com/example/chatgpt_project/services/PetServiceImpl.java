package com.example.chatgpt_project.services;

import com.example.chatgpt_project.entities.Pet;
import com.example.chatgpt_project.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet save(Pet pet) {
        try {
            return petRepository.save(pet);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Pet data is invalid or incomplete.");
        }
    }

    @Override
    public Optional<Pet> findById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()) {
            throw new RuntimeException("Pet not found with id: " + id);
        }
        return pet;
    }


    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<Pet> getAllPets() {
        return List.of();
    }

    @Override
    public Optional<Pet> getPetById(Long id) {
        // Return an Optional containing the pet if it exists, otherwise return Optional.empty()
        return petRepository.findById(id);
    }

    public Pet updatePet(Long id, Pet updatedPet) {
        Optional<Pet> existingPet = petRepository.findById(id);
        if (!existingPet.isPresent()) {
            throw new RuntimeException("Pet not found with id: " + id);
        }
        Pet pet = existingPet.get();
        pet.setName(updatedPet.getName());
        pet.setAnimalType(updatedPet.getAnimalType());
        pet.setBreed(updatedPet.getBreed());
        pet.setAge(updatedPet.getAge());
        return petRepository.save(pet);
    }

    public void deletePetsByName(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }


    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAgeAsc(breed);
    }

    public List<Object[]> getPetNameAndBreed() {
        return petRepository.findPetNameAndBreed();
    }

    public Object[] getPetStatistics() {
        return petRepository.getPetStatistics();
    }

    public Pet createPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getAnimalType() == null) {
            throw new IllegalArgumentException("Pet must have a valid name and animal type.");
        }
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePetName(long id, String name) {
        // Find the pet by its ID
        Optional<Pet> petOptional = petRepository.findById(id);

        // If pet exists, update its name
        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            pet.setName(name); // Update the pet's name
            return petRepository.save(pet); // Save the updated pet
        } else {
            throw new RuntimeException("Pet not found with id " + id);
        }
    }

    @Override
    public void deletePet(Long id) {
        // Check if the pet exists
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id); // Delete the pet by its ID
        } else {
            throw new RuntimeException("Pet not found with id " + id); // If not found, throw an exception
        }
    }

}
