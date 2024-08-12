package com.kenny.sdeappbackend.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private int numberOfWorkingDays;

    @ElementCollection
    private List<String> staffNames;

    @ElementCollection
    private List<String> locations; // Representing classes, rooms, etc.
}
