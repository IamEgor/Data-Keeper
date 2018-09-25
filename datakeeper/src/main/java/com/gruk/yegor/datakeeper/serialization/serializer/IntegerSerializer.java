package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class IntegerSerializer extends BaseSerializer<Integer> {
    @Override
    protected Type getType() {
        return Type.INT;
    }

    /**
     * Serialize {@code int} into byte array with following scheme:
     *
     * @param value target int to serialize.
     * @return specific byte array with scheme.
     */
    public byte[] serialize(@NonNull Integer value) {
        int i = 0xff;
        return new byte[]{
                getFlag(),
                (byte) ((value >>> 24) & i),
                (byte) ((value >>> 16) & i),
                (byte) ((value >>> 8) & i),
                (byte) ((value) & i)
        };
    }

    /**
     * Deserialize {@code int} by {@link #serialize(Integer)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized int
     */
    @NonNull
    @Override
    public Integer deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code int} by {@link #serialize(Integer)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized int
     */
    @NonNull
    @Override
    public Integer deserialize(byte[] bytes, int offset, int lenght) {
        int i = 0xff;
        return ((bytes[4 + offset] & i)) +
                ((bytes[3 + offset] & i) << 8) +
                ((bytes[2 + offset] & i) << 16) +
                ((bytes[1 + offset]) << 24);
    }
}
