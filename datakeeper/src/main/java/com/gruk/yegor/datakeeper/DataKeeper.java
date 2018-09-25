package com.gruk.yegor.datakeeper;

import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;

import java.util.Map;
import java.util.Set;

public interface DataKeeper {

    boolean getBoolean(String key, boolean defaultValue);

    byte getByte(String key, byte defaultValue);

    short getShort(String key, short defaultValue);

    char getChar(String key, char defaultValue);

    int getInt(String key, int defaultValue);

    float getFloat(String key, float defaultValue);

    long getLong(String key, long defaultValue);

    double getDouble(String key, double defaultValue);

    String getString(String key, String defaultValue);

    Set<String> getStringSet(String key, Set<String> defaultValue);

    byte[] getByteArray(String key, byte[] defaultValue);

    <T extends Persistable> T getPersistable(String key, T defaultValue);

    void putBoolean(String key, boolean value);

    void putByte(String key, byte value);

    void putShort(String key, short value);

    void putChar(String key, char value);

    void putInt(String key, int value);

    void putFloat(String key, float value);

    void putLong(String key, long value);

    void putDouble(String key, double value);

    void putString(String key, String value);

    void putStringSet(String key, Set<String> value);

    void putByteArray(String key, byte[] value);

    void putPersistable(String key, Persistable value);

    // TODO: 14.09.2018

    Set<String> keys();

    @Deprecated
    Map<String, ?> getAll();

    boolean contains(String key);

    void remove(String key);

    void clear();
}
