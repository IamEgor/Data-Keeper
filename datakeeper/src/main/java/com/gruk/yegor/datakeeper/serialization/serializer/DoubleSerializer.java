package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class DoubleSerializer extends BaseSerializer<Double> {
    @Override
    protected Type getType() {
        return Type.DOUBLE;
    }

    /**
     * Serialize {@code double} into byte array with following scheme:
     *
     * @param value target double to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Double value) {
        long l = Double.doubleToLongBits(value);
        return new byte[]{
                getFlag(),
                (byte) (l >>> 56),
                (byte) (l >>> 48),
                (byte) (l >>> 40),
                (byte) (l >>> 32),
                (byte) (l >>> 24),
                (byte) (l >>> 16),
                (byte) (l >>> 8),
                (byte) (l)
        };
    }

    /**
     * Deserialize {@code double} by {@link #serialize(Double)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized double
     */
    @NonNull
    @Override
    public Double deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code double} by {@link #serialize(Double)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized double
     */
    @NonNull
    @Override
    public Double deserialize(byte[] bytes, int offset, int length) {
        int i = 0xff;
        long value = ((bytes[8 + offset] & i)) +
                ((bytes[7 + offset] & i) << 8) +
                ((bytes[6 + offset] & i) << 16) +
                ((long) (bytes[5 + offset] & i) << 24) +
                ((long) (bytes[4 + offset] & i) << 32) +
                ((long) (bytes[3 + offset] & i) << 40) +
                ((long) (bytes[2 + offset] & i) << 48) +
                ((long) (bytes[1 + offset]) << 56);
        return Double.longBitsToDouble(value);
    }
}
