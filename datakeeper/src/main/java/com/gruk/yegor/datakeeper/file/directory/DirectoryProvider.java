package com.gruk.yegor.datakeeper.file.directory;

import java.io.File;

/**
 * Define directories for backup, storing, and locking key/values
 */
public interface DirectoryProvider {
    /**
     * Define concrete store directory
     *
     * @return Concrete store directory which will store value files by key
     */
    File getStoreDirectory();
}