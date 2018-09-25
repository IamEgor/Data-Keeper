package com.gruk.yegor.datakeeper.migration;

import android.support.annotation.WorkerThread;

import com.gruk.yegor.datakeeper.DataKeeperBuilder;
import com.gruk.yegor.datakeeper.encryption.KeyEncryption;
import com.gruk.yegor.datakeeper.encryption.ValueEncryption;
import com.gruk.yegor.datakeeper.file.adapter.FileAdapter;
import com.gruk.yegor.datakeeper.file.adapter.NioFileAdapter;
import com.gruk.yegor.datakeeper.file.directory.AndroidDirectoryProvider;
import com.gruk.yegor.datakeeper.file.directory.DirectoryProvider;

public class MigrateProcessor {

    @WorkerThread
    public void migrateFrom(DataKeeperBuilder oldDataKeeperBuilder, DataKeeperBuilder newDataKeeperBuilder) {
        KeyEncryption oldKeyEncryption = oldDataKeeperBuilder.getKeyEncryption();
        ValueEncryption oldValueEncryption = oldDataKeeperBuilder.getValueEncryption();
        DirectoryProvider oldDirectoryProvider = new AndroidDirectoryProvider(oldDataKeeperBuilder.getName(), oldDataKeeperBuilder.getBaseDir());
        FileAdapter oldFileAdapter = new NioFileAdapter(oldDirectoryProvider);

        KeyEncryption newKeyEncryption = newDataKeeperBuilder.getKeyEncryption();
        ValueEncryption newValueEncryption = newDataKeeperBuilder.getValueEncryption();
        DirectoryProvider newDirectoryProvider = new AndroidDirectoryProvider(oldDataKeeperBuilder.getName(), oldDataKeeperBuilder.getBaseDir());
        FileAdapter newFileAdapter = new NioFileAdapter(newDirectoryProvider);

        for (String oldEncryptedName : oldFileAdapter.names()) {
            byte[] encryptedBytes = oldFileAdapter.fetch(oldEncryptedName);
            byte[] plainValue = oldValueEncryption.decrypt(encryptedBytes);
            String plainName = oldKeyEncryption.decrypt(oldEncryptedName);

            oldFileAdapter.remove(oldEncryptedName);

            byte[] newEncryptedValue = newValueEncryption.encrypt(plainValue);
            String newEncryptedName = newKeyEncryption.encrypt(plainName);
            newFileAdapter.save(newEncryptedName, newEncryptedValue);
        }
    }

}
