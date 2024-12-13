package com.example.chatgpt_project.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data // Lombok annotation for getters, setters, `toString`, `equals`, and `hashCode`
@NoArgsConstructor // Lombok annotation for a no-args constructor
@AllArgsConstructor // Lombok annotation for an all-args constructor
public class Household {

    @Id
    private String eircode; // Eircode as the primary key

    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean ownerOccupied;

    // List of pets in the household (can be empty if no pets)
    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<Pet> pets; // A household may have pets (but not every household must)
}
