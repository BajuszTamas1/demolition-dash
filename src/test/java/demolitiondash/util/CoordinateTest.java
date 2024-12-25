package demolitiondash.util;

import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    @DisplayName("Add two Coordinates using the static add function.")
    void add() {
        final int ax = 1;
        final int ay = 2;
        final int bx = 4;
        final int by = 3;
        Coordinate a = new Coordinate(ax, ay);
        Coordinate b = new Coordinate(bx, by);
        Coordinate c = Coordinate.add(a, b);
        assertAll(
                () ->  assertEquals(ax+bx, c.x),
                () -> assertEquals(ay+by, c.y)
        );
    }

    @Test
    @DisplayName("Add two Coordinates using the add function.")
    void testAdd() {
        final int ax = -8;
        final int ay = 10;
        final int bx = 9;
        final int by = 14;
        Coordinate a = new Coordinate(ax, ay);
        Coordinate c = a.add(new Coordinate(bx, by));
        assertAll(
                () ->  assertEquals(ax+bx, c.x),
                () -> assertEquals(ay+by, c.y)
        );
    }

    @Test
    @DisplayName("Multiply a Coordinate with a number using the static multiply function.")
    void multiply() {
        final int m = 5;
        final int ax = 4;
        final int ay = 1;
        Coordinate a = new Coordinate(ax, ay);
        Coordinate b = Coordinate.multiply(a, m);
        assertAll(
                () -> assertEquals(ax*m, b.x),
                () -> assertEquals(ay*m, b.y)
        );
    }

    @Test
    @DisplayName("Multiply a Coordinate with a number using the multiply function.")
    void testMultiply() {
        final int m = 5;
        final int ax = 4;
        final int ay = 1;
        Coordinate a = new Coordinate(ax, ay);
        Coordinate b = a.multiply(m);
        assertAll(
                () -> assertEquals(ax*m, b.x),
                () -> assertEquals(ay*m, b.y)
        );
    }
}