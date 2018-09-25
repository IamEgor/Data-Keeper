package com.gruk.yegor.datakeeper.serialization.serializer;

import com.gruk.yegor.datakeeper.Type;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.PersistableRegistry;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataInput;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataOutput;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.PersistableObjectInput;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.PersistableObjectOutput;

public class PersistableSerializer extends BaseSerializer<Persistable> {

    private final BooleanSerializer booleanSerializer;
    private final ByteSerializer byteSerializer;
    private final ByteArraySerializer byteArraySerializer;
    private final CharSerializer charSerializer;
    private final DoubleSerializer doubleSerializer;
    private final FloatSerializer floatSerializer;
    private final IntegerSerializer integerSerializer;
    private final LongSerializer longSerializer;
    private final ShortSerializer shortSerializer;
    private final StringSerializer stringSerializer;
    private final PersistableRegistry persistableRegistry;

    public PersistableSerializer(BooleanSerializer booleanSerializer,
                                 ByteSerializer byteSerializer,
                                 ByteArraySerializer byteArraySerializer,
                                 CharSerializer charSerializer,
                                 DoubleSerializer doubleSerializer,
                                 FloatSerializer floatSerializer,
                                 IntegerSerializer integerSerializer,
                                 LongSerializer longSerializer,
                                 ShortSerializer shortSerializer,
                                 StringSerializer stringSerializer,
                                 PersistableRegistry persistableRegistry) {
        this.booleanSerializer = booleanSerializer;
        this.byteSerializer = byteSerializer;
        this.byteArraySerializer = byteArraySerializer;
        this.charSerializer = charSerializer;
        this.doubleSerializer = doubleSerializer;
        this.floatSerializer = floatSerializer;
        this.integerSerializer = integerSerializer;
        this.longSerializer = longSerializer;
        this.shortSerializer = shortSerializer;
        this.stringSerializer = stringSerializer;
        this.persistableRegistry = persistableRegistry;
    }

    @Override
    protected Type getType() {
        return Type.PERSISTABLE;
    }

    /**
     * Serialize {@code Persistable} into byte array with following scheme:
     *
     * @param value target persistable to serialize.
     * @return specific byte array with scheme.
     */
    @Override
    public byte[] serialize(Persistable value) {
        DataOutput output = new PersistableObjectOutput(
                booleanSerializer,
                byteSerializer,
                byteArraySerializer,
                charSerializer,
                doubleSerializer,
                floatSerializer,
                integerSerializer,
                longSerializer,
                shortSerializer,
                stringSerializer
        );
        return output.serialize(value);
    }

    @Override
    public Persistable deserialize(byte[] bytes) {
        throw new RuntimeException();
    }

    @Override
    public Persistable deserialize(byte[] bytes, int offset, int length) {
        throw new RuntimeException();
    }

    /**
     * Deserialize {@link Persistable} by {@link #serialize(Persistable)} convention
     *
     * @param key   key for determinate how to serialize
     *              one type of class type or interface type by two or more
     *              different serialization protocols.
     * @param bytes target byte array for deserialization
     * @return deserialized {@link Persistable}
     */
    public Persistable deserialize(String key, byte[] bytes) {
        DataInput input = new PersistableObjectInput(
                booleanSerializer,
                byteSerializer,
                byteArraySerializer,
                charSerializer,
                doubleSerializer,
                floatSerializer,
                integerSerializer,
                longSerializer,
                shortSerializer,
                stringSerializer,
                persistableRegistry
        );
        return input.deserialize(key, bytes);
    }
}
