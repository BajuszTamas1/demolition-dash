package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncreaseBombRadiusPowerupTest {

    @Test
    @DisplayName("The explosion radius of the bombs of the player should increase by one after the player picked up a radius increasing powerup.")
    void effect() {
        Player p = new Player(null, "", 1);
        final int radius = p.getExplosionRadius();
        p.addPowerup(new IncreaseBombRadiusPowerup());
        assertEquals(radius + 1, p.getExplosionRadius());
    }
}