package demolitiondash.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest {
    @Test
    @DisplayName("Size should throw IllegalArgumentException when negative values are passed.")
    void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Size(-1, -5);
        });
    }
}