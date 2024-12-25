package demolitiondash.util.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

public class PlayerStoreTest {

    private PlayerStore playerStore;

    @BeforeEach
    public void setUp() {
        playerStore = PlayerStore.getInstance();
    }

    @Test
    @DisplayName("Test Loading")
    public void testLoadingFromFile() {
        ArrayList<String> players = playerStore.getPlayers();
        assertEquals(3, players.size());
        assertTrue(players.contains("Player 1"));
        assertTrue(players.contains("Player 2"));
        assertTrue(players.contains("Player 3"));
    }

    @Test
    @DisplayName("Test Setting Players")
    public void testSettingPlayers() {
        ArrayList<String> newPlayers = new ArrayList<>();
        newPlayers.add("New Player 1");
        newPlayers.add("New Player 2");
        playerStore.setPlayers(newPlayers);
        assertEquals(newPlayers, playerStore.getPlayers());
    }

    @Test
    @DisplayName("Test Setting Player Hues")
    public void testSettingPlayerHues() {
        ArrayList<Double> newPlayerHues = new ArrayList<>();
        newPlayerHues.add(0.1);
        newPlayerHues.add(0.2);
        playerStore.setPlayerHues(newPlayerHues);
        assertEquals(newPlayerHues, playerStore.getPlayerHues());
    }

    @Test
    @DisplayName("Test Saving To File")
    public void testSavingToFile() {
        playerStore.setPlayers(new ArrayList<String>(List.of("Test Player 1", "Test Player 2")));
        playerStore.setPlayerHues(new ArrayList<Double>(List.of(0.3, 0.4)));
        playerStore.save();
        // Reload the player store to check if data is saved
        playerStore = PlayerStore.getInstance();
        assertEquals(List.of("Test Player 1", "Test Player 2"), playerStore.getPlayers());
        assertEquals(List.of(0.3, 0.4), playerStore.getPlayerHues());
    }
}
