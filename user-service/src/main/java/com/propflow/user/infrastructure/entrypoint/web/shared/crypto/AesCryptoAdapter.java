package com.propflow.user.infrastructure.entrypoint.web.shared.crypto;

import com.propflow.user.domain.port.out.CryptoPort;

public class AesCryptoAdapter implements CryptoPort {
    @Override
    public String encrypt(String plainText) {
        return AesEncryptor.encrypt(plainText);
    }

    @Override
    public String decrypt(String encryptedText) {
        return AesEncryptor.decrypt(encryptedText);
    }

    @Override
    public String hash(String plainText) {
        return HmacHasher.hash(plainText);
    }
}
