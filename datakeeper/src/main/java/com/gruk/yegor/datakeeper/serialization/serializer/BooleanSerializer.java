package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class BooleanSerializer extends BaseSerializer<Boolean> {

    @NonNull
    @Override
    protected Type getType() {
        return Type.BOOL;
    }

    /**
     * Serialize {@code boolean} into byte array with following scheme:
     *
     * @param value target boolean to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Boolean value) {
        return new byte[]{
                getFlag(),
                (byte) (value ? 1 : 0)
        };
    }

    /**
     * Deserialize {@code boolean} by {@link #serialize(Boolean)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized boolean
     */
    @NonNull
    @Override
    public Boolean deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code String} by {@link #serialize(Boolean)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset byte array offset
     * @return deserialized boolean
     */

    @NonNull
    @Override
    public Boolean deserialize(byte[] bytes, int offset, int length) {
        return bytes[1 + offset] != 0;
    }
}
