package demolitiondash.model;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class GameResultTest {
    @Test
    void testTie() {
        GameResult result = GameResult.tie();
        assertTrue(result.isTie());
        assertFalse(result.isWin());
        assertEquals(Optional.empty(), result.getWinner());
        assertEquals("TIE", result.toString());
    }

    @Test
    void testWin() {
        String winner = "Player 1";
        GameResult result = GameResult.win(winner);
        assertFalse(result.isTie());
        assertTrue(result.isWin());
        assertEquals(Optional.of(winner), result.getWinner());
        assertEquals("Player 1 WON", result.toString());
    }
}
