package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationDTO {
    private String groupName;
    private LocalDateTime sendAt;
    private String status;
}
