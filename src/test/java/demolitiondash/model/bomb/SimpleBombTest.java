package demolitiondash.model.bomb;

import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBombTest {
    @Test
    @DisplayName("SimpleBombs explode after 3 seconds.")
    void test() throws InterruptedException {
        SimpleBomb bomb = new SimpleBomb(new Coordinate(0, 0), null);
        assertFalse(bomb.isReadyToExplode());
        Thread.sleep(3000);
        assertTrue(bomb.isReadyToExplode());
    }

}