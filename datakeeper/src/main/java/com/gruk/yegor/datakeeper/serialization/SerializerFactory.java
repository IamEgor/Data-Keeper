package com.gruk.yegor.datakeeper.serialization;

import com.gruk.yegor.datakeeper.Type;
import com.gruk.yegor.datakeeper.exception.SerializationException;
import com.gruk.yegor.datakeeper.serialization.serializer.BooleanSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.ByteArraySerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.ByteSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.CharSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.DoubleSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.FloatSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.IntegerSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.LongSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.PersistableSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.ShortSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.StringSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.StringSetSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.PersistableRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains all serializers which possible for data transformation.
 * This is non-public api class.
 */
public final class SerializerFactory {

    private static final String ZERO_BYTES_MESSAGE = "%s key's value is zero bytes for deserialize";

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
    private final StringSetSerializer stringSetSerializer;
    private final PersistableSerializer persistableSerializer;

    public SerializerFactory(PersistableRegistry persistableRegistry) {
        this.booleanSerializer = new BooleanSerializer();
        this.byteSerializer = new ByteSerializer();
        this.charSerializer = new CharSerializer();
        this.doubleSerializer = new DoubleSerializer();
        this.floatSerializer = new FloatSerializer();
        this.integerSerializer = new IntegerSerializer();
        this.longSerializer = new LongSerializer();
        this.shortSerializer = new ShortSerializer();
        this.stringSerializer = new StringSerializer();
        this.stringSetSerializer = new StringSetSerializer();
        this.byteArraySerializer = new ByteArraySerializer();
        this.persistableSerializer = new PersistableSerializer(
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
    }

    public byte[] serialize(String key, Object object, Type type) {
        switch (type) {
            case BOOL:
                return booleanSerializer.serialize((Boolean) object);
            case BYTE:
                return byteSerializer.serialize((Byte) object);
            case SHORT:
                return shortSerializer.serialize((Short) object);
            case CHAR:
                return charSerializer.serialize((Character) object);
            case INT:
                return integerSerializer.serialize((Integer) object);
            case FLOAT:
                return floatSerializer.serialize((Float) object);
            case LONG:
                return longSerializer.serialize((Long) object);
            case DOUBLE:
                return doubleSerializer.serialize((Double) object);
            case STRING:
                return stringSerializer.serialize((String) object);
            case BYTE_ARRAY:
                return byteArraySerializer.serialize((byte[]) object);
            case STRING_SET:
                return stringSetSerializer.serialize((Set<String>) object);// TODO: 12.09.2018
            case PERSISTABLE:
                return persistableSerializer.serialize((Persistable) object);
            default:
                throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(String key, byte[] bytes) {
        if (bytes.length == 0) {
            throw new SerializationException(String.format(ZERO_BYTES_MESSAGE, key));
        }

        byte flag = bytes[0];

        if (booleanSerializer.isMatches(flag)) {
            return (T) booleanSerializer.deserialize(bytes);
        }
        if (integerSerializer.isMatches(flag)) {
            return (T) integerSerializer.deserialize(bytes);
        }
        if (longSerializer.isMatches(flag)) {
            return (T) longSerializer.deserialize(bytes);
        }
        if (doubleSerializer.isMatches(flag)) {
            return (T) doubleSerializer.deserialize(bytes);
        }
        if (floatSerializer.isMatches(flag)) {
            return (T) floatSerializer.deserialize(bytes);
        }
        if (stringSerializer.isMatches(flag)) {
            return (T) stringSerializer.deserialize(bytes);
        }
        if (stringSetSerializer.isMatches(flag)) {
            return (T) stringSetSerializer.deserialize(bytes);
        }
        if (persistableSerializer.isMatches(flag)) {
            return (T) persistableSerializer.deserialize(key, bytes);
        }
        if (shortSerializer.isMatches(flag)) {
            return (T) shortSerializer.deserialize(bytes);
        }
        if (byteSerializer.isMatches(flag)) {
            return (T) byteSerializer.deserialize(bytes);
        }
        if (byteArraySerializer.isMatches(flag)) {
            return (T) byteArraySerializer.deserialize(bytes);
        }
        if (charSerializer.isMatches(flag)) {
            return (T) charSerializer.deserialize(bytes);
        }
        throw new UnsupportedClassVersionError(String.format("Flag verification failed. Incorrect flag '%s'", flag));
    }

    @SuppressWarnings("unchecked")
    public <T> T redefineMutable(T value) {
        if (value instanceof Persistable) {
            return (T) ((Persistable) value).deepClone();
        }
        if (value instanceof Set) {
            //noinspection unchecked
            Set<String> strings = (Set<String>) value;
            return (T) new HashSet<>(strings);
        }
        return value;
    }

    public BooleanSerializer getBooleanSerializer() {
        return booleanSerializer;
    }

    public ByteSerializer getByteSerializer() {
        return byteSerializer;
    }

    public CharSerializer getCharSerializer() {
        return charSerializer;
    }

    public DoubleSerializer getDoubleSerializer() {
        return doubleSerializer;
    }

    public FloatSerializer getFloatSerializer() {
        return floatSerializer;
    }

    public IntegerSerializer getIntegerSerializer() {
        return integerSerializer;
    }

    public LongSerializer getLongSerializer() {
        return longSerializer;
    }

    public ShortSerializer getShortSerializer() {
        return shortSerializer;
    }

    public StringSerializer getStringSerializer() {
        return stringSerializer;
    }

    public StringSetSerializer getStringSetSerializer() {
        return stringSetSerializer;
    }

    public PersistableSerializer getPersistableSerializer() {
        return persistableSerializer;
    }

    public ByteArraySerializer getByteArraySerializer() {
        return byteArraySerializer;
    }
}