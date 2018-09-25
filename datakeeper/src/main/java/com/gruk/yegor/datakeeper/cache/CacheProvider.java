package com.gruk.yegor.datakeeper.cache;

import android.support.annotation.Nullable;

import com.gruk.yegor.datakeeper.serialization.serializer.BaseSerializer;

import java.util.Map;
import java.util.Set;

/**
 * Describes contract which store, fetch and remove cached elements
 */
public interface CacheProvider {

    /**
     * Returns true if value is exists false otherwise
     *
     * @param key target key
     * @return exists condition
     */
    boolean contains(String key);

    /**
     * Puts file to cache, value not might be null
     *
     * @param <T>        the type parameter
     * @param key        target key
     * @param value      target value
     * @param serializer the serializer
     */
    <T> void put(String key, T value, BaseSerializer<T> serializer);

    /**
     * Returns all keys inside cache
     *
     * @return keys array
     */
    Set<String> keys();

    /**
     * Returns value for target key.
     *
     * @param <T> the type parameter
     * @param key given key for fetching
     * @return actual value if exists, default value otherwise
     */
    @Nullable
    <T> T getValue(String key);

    /**
     * Removes specific value from cache by given key
     *
     * @param key target key for remove
     */
    void remove(String key);

    /**
     * Returns all cached key/values.
     * You should never change this map content.
     *
     * @return target cache key/values
     */
    Map<String, Object> getAll();

    /**
     * Clear files directory.
     */
    void clear();
}