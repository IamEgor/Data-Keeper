package com.gruk.yegor.datakeeper.serialization.serializer;

import android.support.annotation.NonNull;

import com.gruk.yegor.datakeeper.Type;

public class ByteSerializer extends BaseSerializer<Byte> {
    @Override
    protected Type getType() {
        return Type.BYTE;
    }

    /**
     * Serialize {@code byte} into byte array with following scheme:
     *
     * @param value target byte to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(@NonNull Byte value) {
        return new byte[]{
                getFlag(),
                value
        };
    }

    /**
     * Deserialize {@code byte} by {@link #serialize(Byte)}  convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized byte
     */
    @NonNull
    @Override
    public Byte deserialize(byte[] bytes) {
        return deserialize(bytes, 0, 0);
    }

    /**
     * Deserialize {@code byte} by {@link #serialize(Byte)}  convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @return deserialized byte
     */
    @NonNull
    @Override
    public Byte deserialize(byte[] bytes, int offset, int length) {
        return bytes[offset + 1];
    }
}
