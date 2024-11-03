package ru.itis.tjmoney.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String telegram_id;
    private boolean sendingToTelegram;
    private boolean sendingToEmail;
}
