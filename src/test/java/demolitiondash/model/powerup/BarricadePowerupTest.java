package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarricadePowerupTest {
    @Test
    @DisplayName("A player should have more barricades to place down after picking up a Barricade Powerup.")
    void test(){
        Player p = new Player(null, "p", 1);
        final int a = p.getMaxBarrierCount();
        p.addPowerup(new BarricadePowerup());
        assertTrue(a < p.getMaxBarrierCount());
    }

}