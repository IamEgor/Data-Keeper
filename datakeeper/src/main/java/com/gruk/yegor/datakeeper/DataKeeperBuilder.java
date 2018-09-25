package com.gruk.yegor.datakeeper;

import android.content.Context;

import com.gruk.yegor.datakeeper.cache.CacheProvider;
import com.gruk.yegor.datakeeper.cache.NoCacheProvider;
import com.gruk.yegor.datakeeper.encryption.KeyEncryption;
import com.gruk.yegor.datakeeper.encryption.ValueEncryption;
import com.gruk.yegor.datakeeper.exception.EncryptionException;
import com.gruk.yegor.datakeeper.file.adapter.FileAdapter;
import com.gruk.yegor.datakeeper.file.adapter.NioFileAdapter;
import com.gruk.yegor.datakeeper.file.directory.AndroidDirectoryProvider;
import com.gruk.yegor.datakeeper.file.directory.DirectoryProvider;
import com.gruk.yegor.datakeeper.serialization.SerializerFactory;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.PersistableRegistry;

import java.io.File;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The type Data keeper builder.
 */
public final class DataKeeperBuilder {

    /**
     * Default name of preferences which name has not been defined.
     */
    @SuppressWarnings("WeakerAccess")
    public static final String DEFAULT_NAME = "default";

    private final PersistableRegistry persistableRegistry = new PersistableRegistry();

    private File baseDir;
    private String name = DEFAULT_NAME;
    private KeyEncryption keyEncryption = KeyEncryption.NO_OP;
    private ValueEncryption valueEncryption = ValueEncryption.NO_OP;
    private boolean validateEncryptionKeys;

    /**
     * Instantiates a new Data keeper builder.
     *
     * @param context the context
     */
    @Deprecated
    public DataKeeperBuilder(Context context) {
        this.baseDir = context.getFilesDir();
    }

    /**
     * Instantiates a new Data keeper builder.
     *
     * @param baseDir the base dir
     */
    @Deprecated
    public DataKeeperBuilder(File baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Gets persistable registry.
     *
     * @return the persistable registry
     */
    public PersistableRegistry getPersistableRegistry() {
        return persistableRegistry;
    }

    /**
     * Gets base dir.
     *
     * @return the base dir
     */
    public File getBaseDir() {
        return baseDir;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets key encryption.
     *
     * @return the key encryption
     */
    public KeyEncryption getKeyEncryption() {
        return keyEncryption;
    }

    /**
     * Gets value encryption.
     *
     * @return the value encryption
     */
    public ValueEncryption getValueEncryption() {
        return valueEncryption;
    }

    /**
     * Defines preferences name for build instance.
     *
     * @param name target preferences name. Default name is {@link #DEFAULT_NAME}
     * @return current builder instance
     */
    public DataKeeperBuilder name(String name) {
        this.name = name;
        return this;
    }

    // TODO: 13.09.2018
//    /**
//     * * Defines usage of custom directory for preferences saving.
//     * Be careful: write into external directory required appropriate
//     * runtime and manifest permissions.
//     *
//     * @param baseDir base directory for saving.
//     * @return current builder instance
//     */
//    public DataKeeperBuilder customDirectory(File baseDir) {
//        this.baseDir = baseDir;
//        return this;
//    }

    /**
     * Defines key encryption implementation which performs vice versa byte encryption operations.
     * Default value is {@link KeyEncryption#NO_OP}
     *
     * @param keyEncryption key encryption implementation
     * @return current builder instance
     */
    public DataKeeperBuilder keyEncryption(KeyEncryption keyEncryption) {
        this.keyEncryption = keyEncryption;
        return this;
    }

    /**
     * Defines value encryption implementation which performs vice versa byte encryption operations.
     * Default value is {@link ValueEncryption#NO_OP}
     *
     * @param valueEncryption value encryption implementation
     * @return current builder instance
     */
    public DataKeeperBuilder valueEncryption(ValueEncryption valueEncryption) {
        this.valueEncryption = valueEncryption;
        return this;
    }

    /**
     * Validate encryption keys while creating {@link #DataKeeperBuilder}. Throws {@link EncryptionException} if validation wasn't passed
     *
     * @param validateEncryptionKeys validate encryption keys flag
     * @return current builder instance
     */
    public DataKeeperBuilder validateEncryptionKeys(boolean validateEncryptionKeys) {
        this.validateEncryptionKeys = validateEncryptionKeys;
        return this;
    }

    // TODO: 13.09.2018
//    /**
//     * Defines exception handler implementation which handles exception events e.g. logging operations.
//     * Default value is {@link ExceptionHandler#PRINT}
//     *
//     * @param exceptionHandler exception handler implementation
//     * @return current builder instance
//     */
//    public DataKeeperBuilder exceptionHandler(ExceptionHandler exceptionHandler) {
//        this.exceptionHandler = exceptionHandler;
//        return this;
//    }

    /**
     * Registers {@link Persistable} data-object for de/serialization process.
     * All {@link Persistable} data-objects should be registered for understanding
     * de/serialization contract during cache initialization.
     *
     * @param key         target key which uses for fetching {@link Persistable}                    in {@link DataKeeper#putPersistable(String, Persistable)} method
     * @param persistable target class type which implements {@link Persistable} interface
     * @return current builder instance
     */
    public DataKeeperBuilder registerPersistable(String key, Class<? extends Persistable> persistable) {
        persistableRegistry.register(key, persistable);
        return this;
    }

    /**
     * Builds preferences instance with predefined or default parameters.
     *
     * @return preferences instance with predefined or default parameters.
     */
    public DataKeeper build() {
        SerializerFactory serializerFactory = new SerializerFactory(persistableRegistry);
        DirectoryProvider directoryProvider = new AndroidDirectoryProvider(name, baseDir);
        FileAdapter fileAdapter = new NioFileAdapter(directoryProvider);
        CacheProvider cacheProvider = new NoCacheProvider(fileAdapter, keyEncryption, valueEncryption, serializerFactory);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

        DataKeeperImpl dataKeeper = new DataKeeperImpl(
                serializerFactory,
                cacheProvider,
                readWriteLock.readLock(),
                readWriteLock.writeLock()
        );

        if (validateEncryptionKeys) {
            dataKeeper.getAll();
        }

        return dataKeeper;
    }
}
