package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationGroupDTO {
    private int id;
    private String groupName;
    private String sendAt;
    private String status;
}
