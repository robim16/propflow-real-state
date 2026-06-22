package com.propflow.user.infrastructure.entrypoint.web.shared.crypto;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesEncryptorInitializer {

    @Value("${propflow.crypto.aes-secret-key}")
    private String aesSecretKey;

    @PostConstruct
    public void init() {
        AesEncryptor.init(aesSecretKey);
    }
}
