package com.propflow.user.infrastructure.entrypoint.web.shared.crypto;

import com.propflow.user.domain.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AesEncryptor {

    private static final String ALGORITHM        = "AES/GCM/NoPadding";
    private static final int    GCM_TAG_LENGTH   = 128;
    private static final int    GCM_IV_LENGTH    = 12;
    private static final String KEY_ALGORITHM    = "AES";

    // La clave se inyecta desde Config Service vía application.yml
    // nunca hardcodeada en el código fuente
    private static String secretKey;

    public static void init(String key) {
        secretKey = key;
    }

    public static String encrypt(String plainText) {
        try {
            var iv     = generateIv();
            var cipher = buildCipher(Cipher.ENCRYPT_MODE, iv);
            var encrypted = cipher.doFinal(plainText.getBytes());

            // Prefijamos el IV al texto cifrado para poder descifrarlo después
            var combined = new byte[GCM_IV_LENGTH + encrypted.length];
            System.arraycopy(iv,        0, combined, 0,             GCM_IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, GCM_IV_LENGTH, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new EncryptionException("Error cifrando el valor", e);
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            var combined  = Base64.getDecoder().decode(encryptedText);

            var iv        = new byte[GCM_IV_LENGTH];
            var encrypted = new byte[combined.length - GCM_IV_LENGTH];
            System.arraycopy(combined, 0,             iv,        0, GCM_IV_LENGTH);
            System.arraycopy(combined, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

            var cipher    = buildCipher(Cipher.DECRYPT_MODE, iv);
            var decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);

        } catch (Exception e) {
            throw new EncryptionException("Error descifrando el valor", e);
        }
    }

    private static byte[] generateIv() {
        var iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private static Cipher buildCipher(int mode, byte[] iv) throws Exception {
        var keySpec   = new SecretKeySpec(
                Base64.getDecoder().decode(secretKey), KEY_ALGORITHM
        );
        var paramSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        var cipher    = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, keySpec, paramSpec);
        return cipher;
    }
}
