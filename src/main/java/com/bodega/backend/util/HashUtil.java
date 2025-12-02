package com.bodega.backend.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil {

    private HashUtil() {}

    public static String hashPassword(String plain) {
        if (plain == null) {
            throw new IllegalArgumentException("password no puede ser null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo inicializar SHA-256", e);
        }
    }

    public static boolean matches(String plain, String hashed) {
        if (plain == null || hashed == null) return false;
        return hashPassword(plain).equals(hashed);
    }
}
