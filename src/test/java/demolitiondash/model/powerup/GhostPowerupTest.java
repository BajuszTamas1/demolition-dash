package demolitiondash.model.powerup;

import demolitiondash.model.Direction;
import demolitiondash.model.Player;
import demolitiondash.model.Wall;
import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GhostPowerupTest {

    public static MapLayout getDummyLayout() {
        return new MapLayout(
                new ArrayList<>(List.of(new Wall(new Coordinate(0, 1)))),
                5,
                5,
                new ArrayList<>(List.of(new Coordinate(0, 0))),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("A player should be able to cross a wall after picking up a Ghost powerup.")
    void test1() {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "Player", 1);
        p.addPowerup(new GhostPowerup());
        ArrayList<Direction> down = new ArrayList<>(List.of(Direction.DOWN));
        p.move(down);
        map.update();
        assertEquals(0, p.getLocation().x);
        assertEquals(1, p.getLocation().y);
    }

    @Test
    @DisplayName("A player should not be able to plant a bomb inside a wall.")
    void test2() {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "Player", 1);
        p.addPowerup(new GhostPowerup());
        ArrayList<Direction> down = new ArrayList<>(List.of(Direction.DOWN));
        p.move(down);
        map.update();
        assertEquals(0, p.getLocation().x);
        assertEquals(1, p.getLocation().y);
        p.tryPlaceBomb();
        map.update();
        assertEquals(0, map.getBombCount(p));
    }

    @Test
    @DisplayName("A player should die if they are inside a wall after the ghost effect ends.")
    void test3() throws InterruptedException {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "Player", 1);
        p.addPowerup(new GhostPowerup());
        ArrayList<Direction> down = new ArrayList<>(List.of(Direction.DOWN));
        p.move(down);
        map.update();

        Thread.sleep(GhostPowerup.DURATION + 5);
        map.update();

        assertFalse(p.isAlive());
    }

    @Test
    @DisplayName("An invincible player should die if they are inside a wall after the ghost effect ends.")
    void test4() throws InterruptedException {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "Player", 1);
        p.addPowerup(new GhostPowerup());
        Thread.sleep(500);
        p.addPowerup(new InvincibilityPowerup());
        ArrayList<Direction> down = new ArrayList<>(List.of(Direction.DOWN));
        p.move(down);
        map.update();
        Thread.sleep(GhostPowerup.DURATION + 5);
        map.update();

        assertFalse(p.isAlive());
    }
}