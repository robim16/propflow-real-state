package com.propflow.user.domain.port.out;

public interface CryptoPort {
    String encrypt(String plainText);
    String decrypt(String encryptedText);
    String hash(String plainText);
}
