package com.gruk.yegor.datakeeper.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gruk.yegor.datakeeper.encryption.KeyEncryption;
import com.gruk.yegor.datakeeper.encryption.ValueEncryption;
import com.gruk.yegor.datakeeper.file.adapter.FileAdapter;
import com.gruk.yegor.datakeeper.serialization.SerializerFactory;
import com.gruk.yegor.datakeeper.serialization.serializer.BaseSerializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoCacheProvider implements CacheProvider {

    private final FileAdapter fileAdapter;
    private final KeyEncryption keyEncryption;
    private final ValueEncryption valueEncryption;
    private final SerializerFactory serializerFactory;

    public NoCacheProvider(FileAdapter fileAdapter, KeyEncryption keyEncryption, ValueEncryption valueEncryption, SerializerFactory serializerFactory) {
        this.fileAdapter = fileAdapter;
        this.keyEncryption = keyEncryption;
        this.valueEncryption = valueEncryption;
        this.serializerFactory = serializerFactory;
    }

    @Override
    public boolean contains(@NonNull String key) {
        for (String name : fileAdapter.names()) {
            if (key.equals(keyEncryption.decrypt(name))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> void put(String key, T value, BaseSerializer<T> serializer) {
        byte[] serializedValue = serializer.serialize(value);
        byte[] encryptedValue = valueEncryption.encrypt(serializedValue);
        String xorKey = keyEncryption.encrypt(key);
        fileAdapter.save(xorKey, encryptedValue);
        //------------------------------------------------------------------------------------------
//        fileAdapter.updateDirectoryHash();
        //------------------------------------------------------------------------------------------
    }

    @Override
    public Set<String> keys() {
        String[] names = fileAdapter.names();
        Set<String> set = new HashSet<>(names.length);
        for (String name : fileAdapter.names()) {
            set.add(keyEncryption.decrypt(name));
        }
        return set;
    }

    @Override
    @Nullable
    public <T> T getValue(String key) {
        if (!contains(key)) {
            return null;
        }
        String xorKey = keyEncryption.encrypt(key);
        byte[] decryptValue = fileAdapter.fetch(xorKey);
        byte[] serializedValue = valueEncryption.decrypt(decryptValue);
        return serializerFactory.deserialize(key, serializedValue);
    }

    @Override
    public void remove(String key) {
        String encryptedKey = keyEncryption.encrypt(key);
        fileAdapter.remove(encryptedKey);
    }

    @Override
    public Map<String, Object> getAll() {
        String[] names = fileAdapter.names();
        Map<String, Object> objectMap = new HashMap<>(names.length);
        for (String name : names) {
            String key = keyEncryption.decrypt(name);
            byte[] decryptValue = fileAdapter.fetch(name);
            byte[] serializedValue = valueEncryption.decrypt(decryptValue);
            objectMap.put(key, serializerFactory.deserialize(key, serializedValue));
        }
        return objectMap;
    }

    @Override
    public void clear() {
        fileAdapter.clear();
    }
}
