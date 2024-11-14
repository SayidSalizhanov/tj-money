package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GroupMemberDTO {
    private String username;
    private LocalDateTime joinedAt;
    private String role;
}
