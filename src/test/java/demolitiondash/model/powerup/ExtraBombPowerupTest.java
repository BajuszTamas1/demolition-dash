package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraBombPowerupTest {
    @Test
    @DisplayName("A player should have one more bomb after taking up an ExtraBombPowerup.")
    void effect() {
        Player p = new Player(null, "", 1);
        final int count = p.getMaxBombCount();
        p.addPowerup(new ExtraBombPowerup());
        assertEquals(count + 1, p.getMaxBombCount());
    }
}