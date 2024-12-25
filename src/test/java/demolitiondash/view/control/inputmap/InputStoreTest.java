package demolitiondash.view.control.inputmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import demolitiondash.view.control.Action;
import static org.junit.jupiter.api.Assertions.*;

class InputStoreTest {
    private InputStore inputStore;

    @BeforeEach
    void setUp() {
        inputStore = InputStore.getInstance();
    }

    @Test
    @DisplayName("Reset to Default")
    void testResetToDefault() {
        inputStore.resetToDefault();
        ArrayList<InputStoreEntry> controls = inputStore.getControls();
        assertEquals(6, controls.stream().filter((e) -> e.getPlayerNumber() == 1).count());
        assertEquals(6, controls.stream().filter((e) -> e.getPlayerNumber() == 2).count());
        assertEquals(6, controls.stream().filter((e) -> e.getPlayerNumber() == 3).count());
    }

    @Test
    @DisplayName("Set Player Control")
    void testSetPlayerControl() {
        inputStore.resetToDefault();
        assertFalse(inputStore.setPlayerControl(1, KeyEvent.VK_W, "W", Action.MoveUp));
    }

    @Test
    @DisplayName("Invalidate Colliding Controls")
    void testInvalidateCollidingControls() {
        inputStore.resetToDefault();
        inputStore.setPlayerControl(1, KeyEvent.VK_W, "W", Action.MoveUp);
        inputStore.setPlayerControl(2, KeyEvent.VK_W, "W", Action.MoveUp);
        assertTrue(inputStore.hasInvalidateControls());
    }

    @Test
    @DisplayName("Save Controls")
    void testSave() {
        inputStore.resetToDefault();
        inputStore.save();
        assertFalse(inputStore.hasInvalidateControls());
    }

    @Test
    @DisplayName("Get Instance - Singleton")
    void testGetInstance() {
        InputStore instance1 = InputStore.getInstance();
        InputStore instance2 = InputStore.getInstance();
        assertSame(instance1, instance2);
    }
}
