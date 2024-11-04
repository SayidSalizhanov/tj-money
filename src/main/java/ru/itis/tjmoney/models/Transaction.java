package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int id;
    private int userId;
    private int groupId;
    private int amount;
    private String category;
    private String type;
    private LocalDateTime dateTime;
    private String description;
}
