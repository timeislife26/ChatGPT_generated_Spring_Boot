package com.example.chatgpt_project.graphql.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdStatistics {
    private long emptyHouses;
    private long fullHouses;
}
