package demolitiondash.model;

import static org.junit.jupiter.api.Assertions.*;

import demolitiondash.model.map.MapLayout;
import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import demolitiondash.model.map.Map;

import java.util.ArrayList;
import java.util.List;

@DisplayName("PlayerTest")
public class PlayerTest {

    private Map mockMap;

    private static MapLayout getMockLayout() {
        return new MapLayout(
                new ArrayList<>(),
                10,
                10,
                new ArrayList<>(List.of(new Coordinate(0, 0))),
                new ArrayList<>()
        );
    }

    @BeforeEach
    public void setUp() {
        mockMap = new Map(getMockLayout());
    }

    @Test
    @DisplayName("Test Player Constructor")
    public void testPlayerConstructor() {
        Player player = new Player(mockMap, "Test Player", 1);
        assertNotNull(player);
    }

    @Test
    @DisplayName("Test Player Setters and Getters")
    public void testPlayerSettersAndGetters() {
        Player player = new Player(mockMap, "Test Player", 1);
        player.setMaxBombCount(2);
        assertEquals(2, player.getMaxBombCount());
        player.setExplosionRadius(3);
        assertEquals(3, player.getExplosionRadius());
        // Add more assertions for other setters and getters
    }

    @Test
    @DisplayName("Test Player Bomb placement")
    public void testPlayerCanPlaceBomb() {
        Player player = new Player(mockMap, "Test Player", 1);
        assertTrue(player.canPlaceBomb());
        player.tryPlaceBomb();
        mockMap.update();
        mockMap.update();
        assertFalse(player.canPlaceBomb());
    }

    @Test
    @DisplayName("Test Player Barricade placement")
    public void testPlayerCanPlaceBarricade() {
        Player player = new Player(mockMap, "Test Player", 1);
        assertFalse(player.canPlaceBarricade());
        player.setMaxBarrierCount(1);
        assertTrue(player.canPlaceBarricade());
    }
}
