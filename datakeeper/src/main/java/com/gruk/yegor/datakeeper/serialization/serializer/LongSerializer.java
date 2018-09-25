package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class LongSerializer extends BaseSerializer<Long> {
    @Override
    protected Type getType() {
        return Type.LONG;
    }

    /**
     * Serialize {@code long} into byte array with following scheme:
     *
     * @param value target long to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Long value) {
        return new byte[]{
                getFlag(),
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (((long) value))
        };
    }

    /**
     * Deserialize {@code long} by {@link #serialize(Long)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized long
     */
    @NonNull
    @Override
    public Long deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code long} by {@link #serialize(Long)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized long
     */
    @NonNull
    @Override
    public Long deserialize(byte[] bytes, int offset, int length) {
        long l = 0xffL;
        return ((bytes[8 + offset] & l)) +
                ((bytes[7 + offset] & l) << 8) +
                ((bytes[6 + offset] & l) << 16) +
                ((bytes[5 + offset] & l) << 24) +
                ((bytes[4 + offset] & l) << 32) +
                ((bytes[3 + offset] & l) << 40) +
                ((bytes[2 + offset] & l) << 48) +
                (((long) bytes[1 + offset]) << 56);
    }
}
