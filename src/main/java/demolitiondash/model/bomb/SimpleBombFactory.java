package demolitiondash.model.bomb;

import demolitiondash.model.map.Map;
import demolitiondash.model.Player;

public class SimpleBombFactory implements BombFactory{
    private static SimpleBombFactory instance;
    private SimpleBombFactory(){}
    @Override
    public Bomb createBomb(Player player) {
        var location = Map.getNearestTileLocation(player.getCenter());
        return new SimpleBomb(location, player);
    }

    public static SimpleBombFactory getInstance(){
        if(instance == null) instance = new SimpleBombFactory();
        return instance;
    }
}
