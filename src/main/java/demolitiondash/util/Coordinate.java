package demolitiondash.util;

import demolitiondash.model.Direction;

import java.util.Objects;

public class Coordinate implements Cloneable {
    public final int x;
    public final int y;
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Coordinate add(Coordinate a, Coordinate b){
        return new Coordinate(a.x + b.x, a.y + b.y);
    }
    public Coordinate add(Coordinate other){
        return add(this, other);
    }
    public static Coordinate multiply(Coordinate a, int n) {return new Coordinate(a.x * n, a.y*n);}
    public Coordinate multiply(int n){
        return multiply(this, n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public Coordinate clone() {
        try {
            return (Coordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
