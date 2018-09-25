package com.gruk.yegor.datakeeper.file.adapter;

import com.gruk.yegor.datakeeper.exception.FileOperationException;
import com.gruk.yegor.datakeeper.file.directory.DirectoryProvider;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileAdapter implements FileAdapter {

    private static final String ZERO_BYTES_MESSAGE = "%s key's value is zero bytes for saving";
    private static final String R_MODE = "r";
    private static final String RW_MODE = "rw";
    private static final String[] EMPTY_STRING_NAMES_ARRAY = {};

    private final File baseDir;

    public NioFileAdapter(DirectoryProvider directoryProvider) {
        this.baseDir = directoryProvider.getStoreDirectory();
    }

    @Override
    public String[] names() {
        String[] list = baseDir.list();
        if (list == null) {
            return EMPTY_STRING_NAMES_ARRAY;
        }
        return list;
    }

    @Override
    public byte[] fetch(String name) {
        File file = new File(baseDir, name);
        return fetchInternal(file);
    }

    @Override
    public void save(String name, byte[] bytes) {
        if (bytes.length == 0) {
            throw new FileOperationException(String.format(ZERO_BYTES_MESSAGE, name));
        }
        File file = new File(baseDir, name);
        saveInternal(file, bytes);
    }

    @Override
    public void remove(String name) {
        try {
            File file = new File(baseDir, name);
            if (!file.exists()) {
                return;
            }
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        } catch (Exception e) {
            throw new FileOperationException(e);
        }
    }

    @Override
    public void clear() {
        for (File file : baseDir.listFiles()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    private byte[] fetchInternal(File file) {
        FileChannel channel = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, R_MODE);
            channel = randomAccessFile.getChannel();
            int size = (int) randomAccessFile.length();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            byte[] bytes = new byte[size];
            buffer.get(bytes);
            return bytes;
        } catch (Exception e) {
            throw new FileOperationException(e);
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void saveInternal(File file, byte[] bytes) {
        FileChannel channel = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, RW_MODE);
            randomAccessFile.setLength(0);
            channel = randomAccessFile.getChannel();
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
            byteBuffer.put(bytes);
            channel.write(byteBuffer);
            byteBuffer.force();
        } catch (Exception e) {
            throw new FileOperationException(e);
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
