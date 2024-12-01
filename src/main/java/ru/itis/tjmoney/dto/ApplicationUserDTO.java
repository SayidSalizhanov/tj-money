package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationUserDTO {
    private String applicationId;
    private String username;
    private String sendAt;
}
