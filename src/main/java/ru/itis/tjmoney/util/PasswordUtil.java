package ru.itis.tjmoney.util;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {
    private PasswordUtil() {
    }

    public static String encrypt(String password){
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
