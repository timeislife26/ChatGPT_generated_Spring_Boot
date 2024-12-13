package com.example.chatgpt_project.controllers;

import com.example.chatgpt_project.entities.Pet;
import com.example.chatgpt_project.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    // Ensure this method returns Optional<Pet>
    public Optional<Pet> getPetById(Long id) {
        return petService.findById(id); // findById() returns Optional<Pet>
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.status(201).body(createdPet);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Pet> changePetName(@PathVariable Long id, @RequestParam String name) {
        Pet updatedPet = petService.updatePetName(id, name);
        return updatedPet != null ? ResponseEntity.ok(updatedPet) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
