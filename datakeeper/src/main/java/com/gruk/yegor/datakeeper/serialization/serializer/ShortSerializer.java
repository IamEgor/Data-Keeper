package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class ShortSerializer extends BaseSerializer<Short> {
    @Override
    protected Type getType() {
        return Type.SHORT;
    }

    /**
     * Serialize {@code short} into byte array with following scheme:
     *
     * @param value target short to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Short value) {
        return new byte[]{
                getFlag(),
                (byte) (value >>> 8),
                ((byte) ((short) value))
        };
    }

    /**
     * Deserialize {@code short} by {@link #serialize(Short)}  convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized short
     */
    @NonNull
    @Override
    public Short deserialize(byte[] bytes) {
        return deserialize(bytes, 0,0);
    }

    /**
     * Deserialize {@code short} by {@link #serialize(Short)}  convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized short
     */
    @NonNull
    @Override
    public Short deserialize(byte[] bytes, int offset, int length) {
        int i = 0xff;
        return (short) ((bytes[1 + offset] << 8) +
                (bytes[2 + offset] & i));
    }
}
