package com.propflow.user.infrastructure.entrypoint.web.shared.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacHasher {

    private static final String ALGORITHM = "HmacSHA256";
    private static String hmacKey;

    public static void init(String key) {
        hmacKey = key;
    }

    public static String hash(String plainText) {
        try {
            var keySpec = new SecretKeySpec(
                    Base64.getDecoder().decode(hmacKey), ALGORITHM
            );
            var mac = Mac.getInstance(ALGORITHM);
            mac.init(keySpec);
            var result = mac.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("Error generando hash", e);
        }
    }
}
