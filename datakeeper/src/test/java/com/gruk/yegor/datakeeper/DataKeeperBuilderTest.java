package com.gruk.yegor.datakeeper;

import com.gruk.yegor.datakeeper.encryption.AesValueEncryption;
import com.gruk.yegor.datakeeper.encryption.XorKeyEncryption;
import com.gruk.yegor.datakeeper.persistable.Persistable1;
import com.gruk.yegor.datakeeper.persistable.Persistable2;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DataKeeperBuilderTest {

    private static final String PREFS_FOLDER = "covr_prefs";
    private static final String PREFS_NAME = "covr_values";
    private static final byte[] SECRET_KEY_BYTES = "LZN8KKF7KH816D0U".getBytes();
    private static final byte[] INITIAL_VECTOR = "AG6PJGG7AZJD8QPH".getBytes();
    private static final String PERSISTABLE_KEY_1 = "persistable1";
    private static final String PERSISTABLE_KEY_2 = "persistable2";

    private DataKeeper dataKeeper;

    @Before
    public void setUp() throws Exception {
        dataKeeper = new DataKeeperBuilder(new File(PREFS_FOLDER))
                .keyEncryption(new XorKeyEncryption(SECRET_KEY_BYTES))
                .valueEncryption(new AesValueEncryption(SECRET_KEY_BYTES, INITIAL_VECTOR))
                .registerPersistable(PERSISTABLE_KEY_1, Persistable1.class)
                .registerPersistable(PERSISTABLE_KEY_2, Persistable2.class)
                .name(PREFS_NAME)
                .build();
    }

    @Test
    public void putBooleanTest() {
        String key1 = "bool1";
        String key2 = "bool2";
        String key3 = "bool3";
        boolean value1 = true;
        boolean value2 = false;
        dataKeeper.putBoolean(key1, value1);
        dataKeeper.putBoolean(key2, value2);
        assertEquals(dataKeeper.getBoolean(key1, false), value1);
        assertEquals(dataKeeper.getBoolean(key2, false), value2);
        assertEquals(dataKeeper.getBoolean(key1, true), value1);
        assertEquals(dataKeeper.getBoolean(key2, true), value2);
        assertEquals(dataKeeper.getBoolean(key3, value1), value1);
        assertEquals(dataKeeper.getBoolean(key3, value2), value2);
    }

    @Test
    public void putByteTest() {
        String key1 = "byte1";
        String key2 = "byte2";
        String key3 = "byte3";
        byte value1 = 0;
        byte value2 = 1;
        dataKeeper.putByte(key1, value1);
        dataKeeper.putByte(key2, value2);
        assertEquals(dataKeeper.getByte(key1, (byte) 0), value1);
        assertEquals(dataKeeper.getByte(key2, (byte) 0), value2);
        assertEquals(dataKeeper.getByte(key1, (byte) 1), value1);
        assertEquals(dataKeeper.getByte(key2, (byte) 1), value2);
        assertEquals(dataKeeper.getByte(key3, value1), value1);
        assertEquals(dataKeeper.getByte(key3, value2), value2);
    }

    @Test
    public void putShortTest() {
        String key1 = "short1";
        String key2 = "short2";
        String key3 = "short3";
        short value1 = -32768;
        short value2 = 32767;
        dataKeeper.putShort(key1, value1);
        dataKeeper.putShort(key2, value2);
        assertEquals(dataKeeper.getShort(key1, (short) -1), value1);
        assertEquals(dataKeeper.getShort(key2, (short) -1), value2);
        assertEquals(dataKeeper.getShort(key3, value1), value1);
        assertEquals(dataKeeper.getShort(key3, value2), value2);
    }

    @Test
    public void putCharTest() {
        String key1 = "char1";
        String key2 = "char2";
        String key3 = "char3";
        char value1 = 32767;
        char value2 = 'K';
        dataKeeper.putChar(key1, value1);
        dataKeeper.putChar(key2, value2);
        assertEquals(dataKeeper.getChar(key1, (char) 1), value1);
        assertEquals(dataKeeper.getChar(key2, 'c'), value2);
        assertEquals(dataKeeper.getChar(key3, value1), value1);
        assertEquals(dataKeeper.getChar(key3, value2), value2);
    }

    @Test
    public void putIntTest() {
        String key1 = "int1";
        String key2 = "int2";
        String key3 = "int3";
        int value1 = 256;
        int value2 = Integer.MAX_VALUE;
        dataKeeper.putInt(key1, value1);
        dataKeeper.putInt(key2, value2);
        assertEquals(dataKeeper.getInt(key1, -1), value1);
        assertEquals(dataKeeper.getInt(key2, -1), value2);
        assertEquals(dataKeeper.getInt(key3, value1), value1);
        assertEquals(dataKeeper.getInt(key3, value2), value2);
    }

    @Test
    public void putFloatTest() {
        String key1 = "float1";
        String key2 = "float2";
        String key3 = "float3";
        float value1 = 127.6115f;
        float value2 = Float.MAX_VALUE;
        dataKeeper.putFloat(key1, value1);
        dataKeeper.putFloat(key2, value2);
        assertEquals(dataKeeper.getFloat(key1, -1), value1, .0f);
        assertEquals(dataKeeper.getFloat(key2, -1), value2, .0f);
        assertEquals(dataKeeper.getFloat(key3, value1), value1, .0f);
        assertEquals(dataKeeper.getFloat(key3, value2), value2, .0f);
    }

    @Test
    public void putLongTest() {
        String key1 = "long1";
        String key2 = "long2";
        String key3 = "long3";
        long value1 = 200_000_000_000_000L;
        long value2 = Long.MIN_VALUE;
        dataKeeper.putLong(key1, value1);
        dataKeeper.putLong(key2, value2);
        assertEquals(dataKeeper.getLong(key1, -1), value1);
        assertEquals(dataKeeper.getLong(key2, -1), value2);
        assertEquals(dataKeeper.getLong(key3, value1), value1);
        assertEquals(dataKeeper.getLong(key3, value2), value2);
    }

    @Test
    public void putDoubleTest() {
        String key1 = "double1";
        String key2 = "double2";
        String key3 = "double3";
        double value1 = 127.6115;
        double value2 = Double.MAX_VALUE;
        dataKeeper.putDouble(key1, value1);
        dataKeeper.putDouble(key2, value2);
        assertEquals(dataKeeper.getDouble(key1, -1), value1, .0);
        assertEquals(dataKeeper.getDouble(key2, -1), value2, .0);
        assertEquals(dataKeeper.getDouble(key3, value1), value1, .0);
        assertEquals(dataKeeper.getDouble(key3, value2), value2, .0);
    }

    @Test
    public void putStringTest() {
        String key1 = "string1";
        String key2 = "string2";
        String key3 = "string3";
        String value1 = "string1";
        String value2 = "string2";
        dataKeeper.putString(key1, value1);
        dataKeeper.putString(key2, value2);
        assertEquals(dataKeeper.getString(key1, null), value1);
        assertEquals(dataKeeper.getString(key2, null), value2);
        assertEquals(dataKeeper.getString(key3, value1), value1);
        assertEquals(dataKeeper.getString(key3, null), null);
        assertNotEquals(dataKeeper.getString(key3, value1), null);
        assertNotEquals(dataKeeper.getString(key3, null), value1);
    }

    @Test
    public void putStringSetTest() {
        String key1 = "stringSet1";
        String key2 = "stringSet2";
        String key3 = "stringSet3";

        Set<String> value1 = new HashSet<String>() {{
            add("string1");
            add("string2");
        }};
        Set<String> value2 = new HashSet<String>() {{
            add("string3");
            add("string4");
        }};
        dataKeeper.putStringSet(key1, value1);
        dataKeeper.putStringSet(key2, value2);

        assertEquals(dataKeeper.getStringSet(key1, null), value1);
        assertEquals(dataKeeper.getStringSet(key2, null), value2);

        assertEquals(dataKeeper.getStringSet(key3, value1), value1);
        assertEquals(dataKeeper.getStringSet(key3, null), null);
        assertNotEquals(dataKeeper.getStringSet(key3, value1), null);
        assertNotEquals(dataKeeper.getStringSet(key3, null), value1);
    }

    @Test
    public void putByteArrayTest() {
        String key1 = "byteArray1";
        String key2 = "byteArray2";
        String key3 = "byteArray3";

        Random random = new Random();
        byte[] value1 = new byte[8];
        byte[] value2 = new byte[16];
        random.nextBytes(value1);
        random.nextBytes(value2);

        dataKeeper.putByteArray(key1, value1);
        dataKeeper.putByteArray(key2, value2);

        assertArrayEquals(dataKeeper.getByteArray(key1, null), value1);
        assertArrayEquals(dataKeeper.getByteArray(key2, null), value2);

        assertArrayEquals(dataKeeper.getByteArray(key3, value1), value1);
        assertArrayEquals(dataKeeper.getByteArray(key3, null), null);
    }

    @Test
    public void putPersistableTest() {
        String key1 = PERSISTABLE_KEY_1;
        String key2 = PERSISTABLE_KEY_2;
        String key3 = "persistable3";

        Random random = new Random();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        Persistable1 value1 = new Persistable1("string1", bytes);
        Persistable2 value2 = new Persistable2(55, 25.29f, 31.056, (short) 25, true);

        dataKeeper.putPersistable(key1, value1);
        dataKeeper.putPersistable(key2, value2);

        assertEquals(dataKeeper.getPersistable(key1, null), value1);
        assertEquals(dataKeeper.getPersistable(key2, null), value2);

        assertEquals(dataKeeper.getPersistable(key3, value1), value1.deepClone());
        assertEquals(dataKeeper.getPersistable(key3, value2), value2.deepClone());
    }

    @Test
    public void removeTest() {
        String key = "removeString";
        String value = "removeString";
        dataKeeper.putString(key, value);
        assertTrue(dataKeeper.contains(key));
        assertTrue(dataKeeper.keys().contains(key));

        System.gc();//cannot delete file on Windows
        dataKeeper.remove(key);
        assertFalse(dataKeeper.contains(key));
        assertFalse(dataKeeper.keys().contains(key));
    }

    @Test
    public void clearTest() {
        String key = "removeString";
        String value = "removeString";
        dataKeeper.putString(key, value);
        assertFalse(dataKeeper.keys().isEmpty());

        System.gc();//cannot delete file on Windows
        dataKeeper.clear();
        assertTrue(dataKeeper.keys().isEmpty());
    }
}