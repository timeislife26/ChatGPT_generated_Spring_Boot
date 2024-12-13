package com.example.chatgpt_project.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Data // Lombok annotation for getters, setters, `toString`, `equals`, and `hashCode`
@NoArgsConstructor // Lombok annotation for a no-args constructor
@AllArgsConstructor // Lombok annotation for an all-args constructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(nullable = false) // Marks as non-nullable in the database
    private String name;

    @Column(name = "animal_type", nullable = false)
    private String animalType;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private int age;

    // Add the ManyToOne relationship with Household (Many pets belong to one household)
    @ManyToOne
    @JoinColumn(name = "eircode", referencedColumnName = "eircode", nullable = false)
    private Household household;
}
