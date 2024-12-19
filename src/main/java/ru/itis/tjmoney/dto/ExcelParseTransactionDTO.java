package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExcelParseTransactionDTO {
    int amount;
    String type;
    String category;
    LocalDateTime date;
    String description;
}
