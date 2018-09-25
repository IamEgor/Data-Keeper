package com.gruk.yegor.datakeeper.serialization.serializer;

import com.gruk.yegor.datakeeper.Type;

public abstract class BaseSerializer<T> {

    private Type mType;

    public BaseSerializer() {
        mType = getType();
    }

    public byte getFlag() {
        return mType.getFlag();
    }

    public int bytesLength() {
        return mType.getSize();
    }

    public boolean isMatches(byte flag) {
        return flag == mType.getFlag();
    }

    protected abstract Type getType();

    public abstract byte[] serialize(T value);

    public abstract T deserialize(byte[] bytes);

    public abstract T deserialize(byte[] bytes, int offset, int length);
}
