package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    private int id;
    private int userId;
    private int groupId;
    private LocalDateTime joinedAt;
    private String role;
}
