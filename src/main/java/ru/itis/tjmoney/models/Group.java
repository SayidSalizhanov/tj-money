package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private int id;
    private String name;
    private LocalDateTime createdAt;
    private String description;
}
