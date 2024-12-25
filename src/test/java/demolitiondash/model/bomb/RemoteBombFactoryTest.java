package demolitiondash.model.bomb;

import demolitiondash.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoteBombFactoryTest {
    @Test
    @DisplayName("RemoteBombFactory should return RemoteBomb.")
    void test(){
        Player p = new Player(null, "p1", 1);
        RemoteBombFactory factory = RemoteBombFactory.getInstance();
        Bomb b = factory.createBomb(p);
        assertInstanceOf(RemoteBomb.class, b);
    }
}