package com.gruk.yegor.datakeeper.serialization.serializer;

import com.gruk.yegor.datakeeper.Type;

import java.nio.charset.Charset;

public class StringSerializer extends BaseSerializer<String> {
    /**
     * Describes flag offset size
     */
    private static final int FLAG_OFFSET = 1;

    /**
     * String default charset
     */
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    /**
     * Serialize {@code String} into byte array with following scheme:
     *
     * @param value target String to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(String value) {
        byte[] stringBytes = value.getBytes();
        byte[] b = new byte[stringBytes.length + FLAG_OFFSET];
        b[0] = getFlag();
        System.arraycopy(stringBytes, 0, b, FLAG_OFFSET, stringBytes.length);
        return b;
    }

    /**
     * Deserialize {@link String} by {@link #serialize(String)} convention
     *
     * @param bytes target byte array for deserialization
     * @return deserialized String
     */
    @Override
    public String deserialize(byte[] bytes) {
        return deserialize(bytes, 0, bytes.length - 1);
    }

    /**
     * Deserialize {@link String} by {@link #serialize(String)} convention
     *
     * @param bytes  target byte array for deserialization
     * @param offset bytes array offset
     * @param length bytes array length
     * @return deserialized String
     */
    @Override
    public String deserialize(byte[] bytes, int offset, int length) {
        return new String(bytes, FLAG_OFFSET + offset, length, UTF_8_CHARSET);
    }

    @Override
    protected Type getType() {
        return Type.STRING;
    }
}
