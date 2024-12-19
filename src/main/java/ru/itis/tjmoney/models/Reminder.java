package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    private int id;
    private int userId;
    private int groupId;
    private String title;
    private String message;
    private LocalDateTime sendAt;
    private String status;
}
