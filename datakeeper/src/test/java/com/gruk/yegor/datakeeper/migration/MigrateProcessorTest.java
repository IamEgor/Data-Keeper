package com.gruk.yegor.datakeeper.migration;

import com.gruk.yegor.datakeeper.DataKeeper;
import com.gruk.yegor.datakeeper.DataKeeperBuilder;
import com.gruk.yegor.datakeeper.encryption.AesValueEncryption;
import com.gruk.yegor.datakeeper.encryption.XorKeyEncryption;
import com.gruk.yegor.datakeeper.persistable.Persistable1;
import com.gruk.yegor.datakeeper.persistable.Persistable2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MigrateProcessorTest {

    private static final String PREFS_FOLDER = "covr_prefs";
    private static final String PREFS_NAME = "covr_values";
    private static final String PERSISTABLE_KEY_1 = "persistable1";
    private static final String PERSISTABLE_KEY_2 = "persistable2";

    private static final byte[] SECRET_KEY_BYTES = "AZN8KKF7KH816D0U".getBytes();
    private static final byte[] INITIAL_VECTOR = "AG6PJGG7AZJD8QPH".getBytes();
    private static final String KEY_1 = "persistable1";
    private static final String KEY_2 = "persistable2";

    private Persistable1 persistableValue1;
    private Persistable2 persistableValue2;

    @Before
    public void beforeMigrateFrom() {
        DataKeeper dataKeeper = new DataKeeperBuilder(new File(PREFS_FOLDER))
                .name(PREFS_NAME)
                .registerPersistable(PERSISTABLE_KEY_1, Persistable1.class)
                .registerPersistable(PERSISTABLE_KEY_2, Persistable2.class)
                .build();

        dataKeeper.clear();

        Random random = new Random();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        persistableValue1 = new Persistable1("string1", bytes);
        persistableValue2 = new Persistable2(55, 25.29f, 31.056, (short) 25, true);

        dataKeeper.putPersistable(KEY_1, persistableValue1);
        dataKeeper.putPersistable(KEY_2, persistableValue2);

        assertEquals(dataKeeper.getPersistable(KEY_1, null), persistableValue1);
        assertEquals(dataKeeper.getPersistable(KEY_2, null), persistableValue2);
    }

    @Test
    public void migrateFrom() {
        DataKeeperBuilder oldDataKeeperBuilder = new DataKeeperBuilder(new File(PREFS_FOLDER))
                .name(PREFS_NAME)
                .registerPersistable(PERSISTABLE_KEY_1, Persistable1.class)
                .registerPersistable(PERSISTABLE_KEY_2, Persistable2.class);
        DataKeeperBuilder newDataKeeperBuilder = new DataKeeperBuilder(new File(PREFS_FOLDER))
                .keyEncryption(new XorKeyEncryption(SECRET_KEY_BYTES))
                .valueEncryption(new AesValueEncryption(SECRET_KEY_BYTES, INITIAL_VECTOR))
                .name(PREFS_NAME)
                .registerPersistable(PERSISTABLE_KEY_1, Persistable1.class)
                .registerPersistable(PERSISTABLE_KEY_2, Persistable2.class);
        MigrateProcessor migrateProcessor = new MigrateProcessor();
        migrateProcessor.migrateFrom(oldDataKeeperBuilder, newDataKeeperBuilder);
    }

    @After
    public void afterMigrateFrom() {
        DataKeeper newDataKeeper = new DataKeeperBuilder(new File(PREFS_FOLDER))
                .keyEncryption(new XorKeyEncryption(SECRET_KEY_BYTES))
                .valueEncryption(new AesValueEncryption(SECRET_KEY_BYTES, INITIAL_VECTOR))
                .name(PREFS_NAME)
                .registerPersistable(PERSISTABLE_KEY_1, Persistable1.class)
                .registerPersistable(PERSISTABLE_KEY_2, Persistable2.class)
                .build();

        assertEquals(newDataKeeper.getPersistable(KEY_1, null), persistableValue1);
        assertEquals(newDataKeeper.getPersistable(KEY_2, null), persistableValue2);
    }
}