package ru.itis.tjmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserGroupDTO {
    private int groupId;
    private String groupName;
    private String description;
    private String role;
}
