package demolitiondash.model.bomb;

import demolitiondash.model.map.Map;
import demolitiondash.model.Player;

public class RemoteBombFactory implements BombFactory{
    private static RemoteBombFactory instance;
    private RemoteBombFactory(){}
    @Override
    public Bomb createBomb(Player player) {
        var location = Map.getNearestTileLocation(player.getCenter());
        return new RemoteBomb(location, player);
    }

    public static RemoteBombFactory getInstance(){
        if(instance == null) instance = new RemoteBombFactory();
        return instance;
    }
}
