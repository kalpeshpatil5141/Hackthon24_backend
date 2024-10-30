package com.hackathon24backend.util;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
@Service
public class EncryptionUtil {
    private static final String ALGORITHM = "AES";

    // Encrypt a plain text key
    public static String encrypt(String plainText, String secretKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Generate a secret key (Store this securely)
    public static String generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // AES key size in bits
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String secretKey = generateSecretKey();
        System.out.println("secreteKey"+secretKey);
        String keyToEncrypt = "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"; // Key you want to encrypt

        String encryptedKey = encrypt(keyToEncrypt, secretKey);
        System.out.println("Encrypted Key: " + encryptedKey);
    }
}

