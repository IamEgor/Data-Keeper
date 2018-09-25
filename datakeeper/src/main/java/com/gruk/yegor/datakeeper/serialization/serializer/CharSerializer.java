package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class CharSerializer extends BaseSerializer<Character> {
    @Override
    protected Type getType() {
        return Type.CHAR;
    }

    /**
     * Serialize {@code char} into byte array with following scheme:
     *
     * @param value target char to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Character value) {
        return new byte[]{
                getFlag(),
                (byte) (value >>> 8),
                ((byte) ((char) value))
        };
    }

    /**
     * Deserialize {@code char} by {@link #serialize(Character)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized char
     */
    @NonNull
    @Override
    public Character deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code char} by {@link #serialize(Character)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized char
     */
    @NonNull
    @Override
    public Character deserialize(byte[] bytes, int offset, int length) {
        int i = 0xFF;
        return (char) ((bytes[1 + offset] << 8) +
                (bytes[2 + offset] & i));
    }
}
