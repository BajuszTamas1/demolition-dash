package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeedIncreasePowerupTest {

    @Test
    @DisplayName("A player should be faster after taking up a SpeedIncreasePowerup.")
    void effect() {
        Player p = new Player(null, "", 1);
        final int speed1 = p.getSpeed();
        p.addPowerup(new SpeedIncreasePowerup());
        assertTrue(speed1 < p.getSpeed());
    }
}