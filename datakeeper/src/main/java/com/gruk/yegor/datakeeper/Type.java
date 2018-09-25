package com.gruk.yegor.datakeeper;

public enum Type {
    BOOL((byte) 1, 2),
    BYTE((byte) 2, 2),
    SHORT((byte) 3, 3),
    CHAR((byte) 4, 3),
    INT((byte) 5, 5),
    FLOAT((byte) 6, 5),
    LONG((byte) 7, 9),
    DOUBLE((byte) 8, 9),
    STRING((byte) 9, 1),
    BYTE_ARRAY((byte) 10, 1),
    STRING_SET((byte) 11, 1),

    PERSISTABLE((byte) 12, -1);// TODO: 14.09.2018

    Type(byte flag, int size) {
        this.mFlag = flag;
        this.mSize = size;
    }

    /**
     * Uses for detecting byte array primitive type
     */
    private byte mFlag;
    /**
     * Minimum size. Flag + type size
     */
    private int mSize;

    public byte getFlag() {
        return mFlag;
    }

    public int getSize() {
        return mSize;
    }
}
