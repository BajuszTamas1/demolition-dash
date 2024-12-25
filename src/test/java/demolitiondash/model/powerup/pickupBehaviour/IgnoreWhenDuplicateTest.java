package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IgnoreWhenDuplicateTest {

    private static int result = 0;

    static class MockPowerup extends Powerup {
        private final int value;
        public MockPowerup(int value){
            super(IgnoreWhenDuplicate.getInstance());
            this.value = value;
        }
        public void effect(){
            result = value;
        }
        @Override
        public void clear() {
        }
    }
    @Test
    @DisplayName("Taking up the same type of powerup should be ignored.")
    void test1(){
        Player p = new Player(null, "p1", 1);
        new MockPowerup(1).activate(p);
        new MockPowerup(2).activate(p);
        assertEquals(1, result);
    }

}