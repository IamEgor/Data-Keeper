package com.gruk.yegor.datakeeper.exception;

/**
 * Exception will be thrown if decrypted data has zero bytes and cannot be deserialized
 */
public final class SerializationException extends RuntimeException {

    public SerializationException(String msg) {
        super(msg);
    }
}