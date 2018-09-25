package com.gruk.yegor.datakeeper.file.directory;

import com.gruk.yegor.datakeeper.exception.FileOperationException;

import java.io.File;

/**
 * Provides default android cache directory or external (if possible) cache directory.
 */
public class AndroidDirectoryProvider implements DirectoryProvider {

    private static final String CANNOT_CREATE_DIR_MESSAGE = "Can't create preferences directory in %s";
    private static final String STORE_DIRECTORY_NAME = "values";

    private final File storeDirectory;

    /**
     * Creates instance for default or external (if enabled) persistent cache directory.
     *
     * @param prefName preferences name
     * @param baseDir  all data will be saved inside this directory.
     */
    public AndroidDirectoryProvider(String prefName, File baseDir) {
        this.storeDirectory = createAndValidate(baseDir, prefName, STORE_DIRECTORY_NAME);
    }

    @Override
    public File getStoreDirectory() {
        return storeDirectory;
    }

    private File createAndValidate(File baseDir, String prefName, String subDirectory) {
        File targetDirectory = create(baseDir, prefName, subDirectory);
        if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
            String absolutePath = targetDirectory.getAbsolutePath();
            throw new FileOperationException(String.format(CANNOT_CREATE_DIR_MESSAGE, absolutePath));
        }
        return targetDirectory;
    }

    private File create(File baseDir, String prefName, String subDirectory) {
        File prefNameDir = new File(baseDir, prefName);
        return new File(prefNameDir, subDirectory);
    }
}
