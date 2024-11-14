package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionDTO {
    private int amount;
    private String category;
    private String type;
    private String username;
    private String description;
}
