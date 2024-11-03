package ru.itis.tjmoney.exceptions;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }
}
