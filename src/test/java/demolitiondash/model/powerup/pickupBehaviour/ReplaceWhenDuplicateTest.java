package demolitiondash.model.powerup.pickupBehaviour;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.Powerup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceWhenDuplicateTest {
    static class MockPowerup extends Powerup {
        public MockPowerup(){
            super(ReplaceWhenDuplicate.getInstance());
        }
        public void effect(){
        }
        @Override
        public void clear() {
        }
    }
    @Test
    @DisplayName("Taking up the same type of powerup should be result in replacing.")
    void test1(){
        Player p = new Player(null, "p1", 1);
        MockPowerup pw1 = new MockPowerup();
        MockPowerup pw2 = new MockPowerup();

        pw1.activate(p);
        pw2.activate(p);

        assertEquals(1, p.getPowerups().size());
        assertEquals(pw2, p.getPowerups().get(0));
    }

}