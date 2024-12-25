package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvincibilityPowerupTest {
    @Test
    @DisplayName("A player with Invincibility should not die.")
    void test1(){
        Player p = new Player(null, "p1", 1);
        p.addPowerup(new InvincibilityPowerup());
        p.die();
        assertTrue(p.isAlive());
    }

    @Test
    @DisplayName("A player with Invincibility should die when forceDie() is used.")
    void test2(){
        Player p = new Player(null, "p1", 1);
        p.addPowerup(new InvincibilityPowerup());
        p.forceDie();
        assertFalse(p.isAlive());
    }

    @Test
    @DisplayName("Invincibility should expire after the specified time.")
    void test3() throws InterruptedException {
        Map map = new Map(MapLoader.getInstance().getLayout(0));
        Player p = new Player(map, "p1", 1);
        p.addPowerup(new InvincibilityPowerup());
        p.die();
        assertTrue(p.isAlive());
        Thread.sleep(InvincibilityPowerup.DURATION + 5);
        p.update();
        p.die();
        assertFalse(p.isAlive());
    }

}