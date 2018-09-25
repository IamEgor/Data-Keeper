package com.gruk.yegor.datakeeper.encryption;

import com.gruk.yegor.datakeeper.exception.EncryptionException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public final class AesValueEncryptionTest {

    private static final byte[] SECRET_KEY_BYTES = "LZN8KKF7KH816D0U".getBytes();
    private static final byte[] INITIAL_VECTOR = "AG6PJGG7AZJD8QPH".getBytes();
    private static final byte[] BAD_SECRET_KEY_BYTES = "0000000000000000".getBytes();
    private static final byte[] BAD_INITIAL_VECTOR = "0000000000000000".getBytes();

    private final ValueEncryption encryption = new AesValueEncryption(SECRET_KEY_BYTES, INITIAL_VECTOR);
    private final ValueEncryption badEncryption = new AesValueEncryption(BAD_SECRET_KEY_BYTES, BAD_INITIAL_VECTOR);

    @Test
    public void encryptDecrypt() {
        String original = "some string";
        byte[] originalBytes = original.getBytes();

        byte[] encrypt = encryption.encrypt(originalBytes);
        byte[] decrypt = encryption.decrypt(encrypt);
        String encryptedString = new String(encrypt);
        String restored = new String(decrypt);

        assertNotEquals(original, encryptedString);
        assertEquals(original, restored);
    }

    @Test(expected = EncryptionException.class)
    public void badDecrypt() {
        String original = "some string";
        byte[] originalBytes = original.getBytes();

        byte[] encrypt = encryption.encrypt(originalBytes);

        badEncryption.decrypt(encrypt);
    }

    @Test(expected = EncryptionException.class)
    public void incorrectKeySize() {
        byte[] secretKeyBytes = {};
        new AesValueEncryption(secretKeyBytes, secretKeyBytes);
    }
}