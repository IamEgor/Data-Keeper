package com.gruk.yegor.datakeeper.serialization.serializer;

import com.gruk.yegor.datakeeper.Type;

public class ByteArraySerializer extends BaseSerializer<byte[]> {

    @Override
    protected Type getType() {
        return Type.BYTE_ARRAY;
    }

    /**
     * Describes flag offset size
     */
    private static final int FLAG_OFFSET = 1;

    /**
     * Serialize {@code byte[]} into byte array with following scheme:
     *
     * @param bytes target byte array to serialize.
     * @return specific byte array with scheme.
     */

    public byte[] serialize(byte[] bytes) {
        byte[] b = new byte[bytes.length + FLAG_OFFSET];
        b[0] = getFlag();
        System.arraycopy(bytes, 0, b, FLAG_OFFSET, bytes.length);
        return b;
    }

    /**
     * Deserialize byte by {@link #serialize(byte[])} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized {@code byte[]}
     */
    public byte[] deserialize(byte[] bytes) {
        return deserialize(bytes, 0, bytes.length - 1);
    }

    /**
     * Deserialize byte by {@link #serialize(byte[])} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @param length bytes array length
     * @return deserialized {@code byte[]}
     */
    public byte[] deserialize(byte[] bytes, int offset, int length) {
        byte[] raw = new byte[length];
        System.arraycopy(bytes, FLAG_OFFSET + offset, raw, 0, length);
        return raw;
    }
}
