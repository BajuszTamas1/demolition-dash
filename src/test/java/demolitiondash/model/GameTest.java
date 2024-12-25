package demolitiondash.model;

import static org.junit.jupiter.api.Assertions.*;


import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.util.Coordinate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DisplayName("GameTest")
public class GameTest {

    private static MapLayout getMockLayout() {
        return new MapLayout(
                new ArrayList<>(),
                10,
                10,
                new ArrayList<>(List.of(new Coordinate(0, 0), new Coordinate(5,5))),
                new ArrayList<>()
        );
    }
    @Test
    @DisplayName("Test Game InitMap Method")
    public void testGameInitMap() {
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        Game game = new Game(getMockLayout(), playerNames, 3);
        game.initMap();
        Map map = game.getMap();
        assertNotNull(map);
    }

    @Test
    @DisplayName("Test Game Update Method")
    public void testGameUpdate() {
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        Game game = new Game(getMockLayout(), playerNames, 3);
        game.initMap();
        game.update();
    }

    @Test
    @DisplayName("Test Game isRoundOver Method")
    public void testGameIsRoundOver() {
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        Game game = new Game(getMockLayout(), playerNames, 3);
        game.initMap();
        assertFalse(game.isRoundOver());
    }

    @Test
    @DisplayName("Test Game GetRoundResult Method")
    public void testGameGetRoundResult() {
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        Game game = new Game(getMockLayout(), playerNames, 3);
        game.initMap();
        Optional<GameResult> roundResult = game.getRoundResult();
        assertFalse(roundResult.isPresent());
    }

}
