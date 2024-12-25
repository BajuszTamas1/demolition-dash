package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("MonsterTest")
    public class MonsterTest {
        private Map mockMap;

        private static MapLayout getMockLayout() {
            return new MapLayout(
                    new ArrayList<>(),
                    10,
                    10,
                    new ArrayList<>(),
                    new ArrayList<>(List.of(new Coordinate(0, 0)))
            );
        }

        @BeforeEach
        public void setUp() {
            mockMap = new Map(getMockLayout());
        }

        @Test
        @DisplayName("Test Monster Constructor")
        public void testMonsterConstructor() {
            Monster monster = new Monster(mockMap);
            assertNotNull(monster);
        }

        @Test
        @DisplayName("Test Monster Update")
        public void testMonsterUpdate() {
            Monster monster = new Monster(mockMap);
            monster.setDirection(Direction.RIGHT);
            Coordinate initialLocation = monster.getLocation();
            monster.update();
            assertNotEquals(initialLocation, monster.getLocation());
        }

        @Test
        @DisplayName("Test Monster GetHitbox")
        public void testMonsterGetHitbox() {
            Monster monster = new Monster(mockMap);
            Boundary hitbox = monster.getHitbox();
            assertNotNull(hitbox);
        }

 }
