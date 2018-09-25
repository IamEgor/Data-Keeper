package com.gruk.yegor.datakeeper;

import com.gruk.yegor.datakeeper.cache.CacheProvider;
import com.gruk.yegor.datakeeper.serialization.SerializerFactory;
import com.gruk.yegor.datakeeper.serialization.serializer.BaseSerializer;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class DataKeeperImpl implements DataKeeper {

    private final SerializerFactory serializerFactory;
    private final CacheProvider cacheProvider;
    private final Lock readLock;
    private final Lock writeLock;

    public DataKeeperImpl(SerializerFactory serializerFactory, CacheProvider cacheProvider, Lock readLock, Lock writeLock) {
        this.serializerFactory = serializerFactory;
        this.cacheProvider = cacheProvider;
        this.readLock = readLock;
        this.writeLock = writeLock;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return getValue(key, defValue);
    }

    @Override
    public byte getByte(String key, byte defValue) {
        return getValue(key, defValue);
    }

    @Override
    public short getShort(String key, short defValue) {
        return getValue(key, defValue);
    }

    @Override
    public char getChar(String key, char defValue) {
        return getValue(key, defValue);
    }

    @Override
    public int getInt(String key, int defValue) {
        return getValue(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return getValue(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return getValue(key, defValue);
    }

    @Override
    public double getDouble(String key, double defValue) {
        return getValue(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return getValue(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return getValue(key, defValue);
    }

    @Override
    public byte[] getByteArray(String key, byte[] defValue) {
        return getValue(key, defValue);
    }

    @Override
    public <T extends Persistable> T getPersistable(String key, T defValue) {
        return getValue(key, defValue);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        putValue(key, value, serializerFactory.getBooleanSerializer());
    }

    @Override
    public void putByte(String key, byte value) {
        putValue(key, value, serializerFactory.getByteSerializer());
    }

    @Override
    public void putShort(String key, short value) {
        putValue(key, value, serializerFactory.getShortSerializer());
    }

    @Override
    public void putChar(String key, char value) {
        putValue(key, value, serializerFactory.getCharSerializer());
    }

    @Override
    public void putInt(String key, int value) {
        putValue(key, value, serializerFactory.getIntegerSerializer());
    }

    @Override
    public void putFloat(String key, float value) {
        putValue(key, value, serializerFactory.getFloatSerializer());
    }

    @Override
    public void putLong(String key, long value) {
        putValue(key, value, serializerFactory.getLongSerializer());
    }

    @Override
    public void putDouble(String key, double value) {
        putValue(key, value, serializerFactory.getDoubleSerializer());
    }

    @Override
    public void putString(String key, String value) {
        if (value == null) {
            remove(key);
            return;
        }
        putValue(key, value, serializerFactory.getStringSerializer());
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        if (value == null) {
            remove(key);
            return;
        }
        putValue(key, value, serializerFactory.getStringSetSerializer());
    }

    @Override
    public void putPersistable(String key, Persistable value) {
        if (value == null) {
            remove(key);
            return;
        }
        putValue(key, value, serializerFactory.getPersistableSerializer());
    }

    @Override
    public void putByteArray(String key, byte[] value) {
        putValue(key, value, serializerFactory.getByteArraySerializer());
    }

    @Override
    public Set<String> keys() {
        readLock.lock();
        try {
            return cacheProvider.keys();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Map<String, ?> getAll() {
        readLock.lock();
        try {
            Map<String, Object> all = cacheProvider.getAll();
            Map<String, Object> clone = new HashMap<>(all.size());
            for (String key : all.keySet()) {
                Object value = all.get(key);
                Object redefinedValue = serializerFactory.redefineMutable(value);
                clone.put(key, redefinedValue);
            }
            return Collections.unmodifiableMap(clone);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(String key) {
        readLock.lock();
        try {
            return cacheProvider.contains(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void remove(String key) {
        writeLock.lock();
        try {
            cacheProvider.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            cacheProvider.clear();
        } finally {
            writeLock.unlock();
        }
    }

    private <T> T getValue(String key, T defValue) {
        readLock.lock();
        try {
            // TODO: 25.09.2018  
//            cacheProvider.contains(key);
            T value = cacheProvider.getValue(key);
            if (value == null) {
                return defValue;
            }
            return serializerFactory.redefineMutable(value);
        } finally {
            readLock.unlock();
        }
    }

    private <T> void putValue(String key, T value, BaseSerializer<T> serializer) {
        writeLock.lock();
        try {
            cacheProvider.put(key, value, serializer);
        } finally {
            writeLock.unlock();
        }
    }
}