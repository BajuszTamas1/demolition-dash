package demolitiondash.model.bomb;

import demolitiondash.model.Player;
import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.model.map.MapLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBombFactoryTest {

    @Test
    @DisplayName("SimpleBombFactory should create SimpleBombs.")
    void test(){
        Player p = new Player(null, "p1", 1);
        SimpleBombFactory factory = SimpleBombFactory.getInstance();
        Bomb b = factory.createBomb(p);
        assertInstanceOf(SimpleBomb.class, b);
    }

}