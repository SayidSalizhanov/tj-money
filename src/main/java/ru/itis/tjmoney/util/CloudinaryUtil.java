package ru.itis.tjmoney.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public final class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "dfmh8zgee");
            configMap.put("api_key", "864998561278635");
            configMap.put("api_secret", "Vr4fsd2vm2OrybMZKdsjPkDG52U");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
}
