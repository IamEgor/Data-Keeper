package com.gruk.yegor.datakeeper.persistable;

import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataInput;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataOutput;

import java.util.Arrays;

public class Persistable1 implements Persistable {

    private String string;
    private byte[] bytes;

    public Persistable1() {
    }

    public Persistable1(String string, byte[] bytes) {
        this.string = string;
        this.bytes = bytes;
    }

    @Override
    public void writeExternal(DataOutput out) {
        out.writeString(string);
        out.writeByteArray(bytes);
    }

    @Override
    public void readExternal(DataInput in) {
        string = in.readString();
        bytes = in.readByteArray();
    }

    @Override
    public Persistable deepClone() {
        return new Persistable1(string, bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persistable1)) return false;

        Persistable1 that = (Persistable1) o;

        if (string != null ? !string.equals(that.string) : that.string != null) return false;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = string != null ? string.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Persistable1{" +
                "string='" + string + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
