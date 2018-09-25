package com.gruk.yegor.datakeeper.persistable;

import com.gruk.yegor.datakeeper.serialization.serializer.persistable.Persistable;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataInput;
import com.gruk.yegor.datakeeper.serialization.serializer.persistable.io.DataOutput;

public class Persistable2 implements Persistable {

    private int anInt;
    private float aFloat;
    private double aDouble;
    private short aShort;
    private boolean aBoolean;

    public Persistable2() {
    }

    public Persistable2(int anInt, float aFloat, double aDouble, short aShort, boolean aBoolean) {
        this.anInt = anInt;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.aShort = aShort;
        this.aBoolean = aBoolean;
    }

    public int getAnInt() {
        return anInt;
    }

    public float getaFloat() {
        return aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public short getaShort() {
        return aShort;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    @Override
    public void writeExternal(DataOutput out) {
        out.writeInt(anInt);
        out.writeFloat(aFloat);
        out.writeDouble(aDouble);
        out.writeShort(aShort);
        out.writeBoolean(aBoolean);
    }

    @Override
    public void readExternal(DataInput in) {
        anInt = in.readInt();
        aFloat = in.readFloat();
        aDouble = in.readDouble();
        aShort = in.readShort();
        aBoolean = in.readBoolean();
    }

    @Override
    public Persistable deepClone() {
        return new Persistable2(anInt, aFloat, aDouble, aShort, aBoolean);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persistable2)) return false;

        Persistable2 that = (Persistable2) o;

        if (anInt != that.anInt) return false;
        if (Float.compare(that.aFloat, aFloat) != 0) return false;
        if (Double.compare(that.aDouble, aDouble) != 0) return false;
        if (aShort != that.aShort) return false;
        return aBoolean == that.aBoolean;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = anInt;
        result = 31 * result + (aFloat != +0.0f ? Float.floatToIntBits(aFloat) : 0);
        temp = Double.doubleToLongBits(aDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) aShort;
        result = 31 * result + (aBoolean ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Persistable2{" +
                "anInt=" + anInt +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", aShort=" + aShort +
                ", aBoolean=" + aBoolean +
                '}';
    }
}
