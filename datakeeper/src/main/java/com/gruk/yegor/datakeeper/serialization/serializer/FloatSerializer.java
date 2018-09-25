package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class FloatSerializer extends BaseSerializer<Float> {
    @Override
    protected Type getType() {
        return Type.FLOAT;
    }

    /**
     * Serialize {@code float} into byte array with following scheme:
     *
     * @param value target float to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Float value) {
        int val = Float.floatToIntBits(value);
        return new byte[]{
                getFlag(),
                (byte) (val >>> 24),
                (byte) (val >>> 16),
                (byte) (val >>> 8),
                (byte) (val)
        };
    }

    /**
     * Deserialize {@code float} by {@link #serialize(Float)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized float
     */
    @NonNull
    @Override
    public Float deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code float} by {@link #serialize(Float)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized float
     */

    @NonNull
    @Override
    public Float deserialize(byte[] bytes, int offset, int length) {
        int i = 0xFF;
        int value = ((bytes[4 + offset] & i)) +
                ((bytes[3 + offset] & i) << 8) +
                ((bytes[2 + offset] & i) << 16) +
                ((bytes[1 + offset]) << 24);
        return Float.intBitsToFloat(value);
    }
}
