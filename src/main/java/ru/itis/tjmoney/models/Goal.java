package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    private int id;
    private int userId;
    private int groupId;
    private String title;
    private String description;
    private int progress;
}
