package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecordDTO {
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
}
