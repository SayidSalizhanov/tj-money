package ru.itis.tjmoney.exceptions;

public class GroupMemberNotFoundException extends RuntimeException {
    public GroupMemberNotFoundException(String message) {
        super(message);
    }
}
