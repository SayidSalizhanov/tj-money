package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GroupDTO {
    private int id;
    private String name;
    private String createdAt;
    private String description;
}
