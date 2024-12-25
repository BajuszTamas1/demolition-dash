package demolitiondash.model.powerup;

import demolitiondash.model.Direction;
import demolitiondash.model.Entity;
import demolitiondash.model.Player;
import demolitiondash.model.bomb.RemoteBomb;
import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class RemoteBombPowerupTest {
    private MapLayout getDummyLayout() {
        return new MapLayout(new ArrayList<>(),
                10,
                10,
                new ArrayList<>(List.of(new Coordinate(0, 0))),
                new ArrayList<>());
    }

    @Test
    @DisplayName("A player should put down RC bombs after picking up and RC powerup.")
    void effect() {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "p1", 1);
        p.addPowerup(new RemoteBombsPowerup());

        p.tryPlaceBomb();
        map.update();
        Entity e = map.getStaticElems().get(0);
        assertInstanceOf(RemoteBomb.class, e);
    }

    @Test
    @DisplayName("The bombs of the player should explode when the player tries to put down another one after hitting their limit.")
    void explosion() {
        Map map = new Map(getDummyLayout());
        Player p = new Player(map, "p1", 1);
        p.addPowerup(new RemoteBombsPowerup());

        ArrayList<Direction> fwd = new ArrayList<>(List.of(Direction.DOWN));

        while (map.getBombCount(p) < p.getMaxBombCount()) {
            p.tryPlaceBomb();
            map.update();
            p.move(fwd);
            map.update();
        }
        assertEquals(p.getMaxBombCount(), map.getBombCount(p));

        p.move(fwd);
        map.update();
        p.tryPlaceBomb();
        map.update();
        map.update();

        assertEquals(0, map.getBombCount(p));
    }
}
