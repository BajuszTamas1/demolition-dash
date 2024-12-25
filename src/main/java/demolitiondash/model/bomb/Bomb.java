package demolitiondash.model.bomb;

import demolitiondash.model.Entity;
import demolitiondash.model.Explosion;
import demolitiondash.model.map.Map;
import demolitiondash.model.Player;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

public abstract class Bomb extends Entity {
    protected final Player owner;
    protected static final Size BOMB_SIZE = new Size(Map.TILE_SIZE, Map.TILE_SIZE);
    protected boolean isExploded;

    /**
     * Constructs a new Bomb object with the specified location and owner.
     *
     * @param location The coordinate location of the bomb.
     * @param owner    The player who owns the bomb.
     */
    public Bomb(Coordinate location, Player owner) {
        super(location, BOMB_SIZE);
        this.owner = owner;
    }

    /**
     * Checks if the bomb is ready to be disposed.
     *
     * @return True if the bomb has exploded, otherwise false.
     */
    @Override
    public boolean isReadyToGetDisposed() {
        return isExploded;
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Determines if the bomb is ready to explode.
     *
     * @return True if the bomb is ready to explode, otherwise false.
     */
    public abstract boolean isReadyToExplode();

    /**
     * Explodes the bomb on the map, creating an explosion object.
     *
     * @param map The map on which the explosion occurs.
     * @return The explosion object created by the bomb.
     */
    public Explosion explode(Map map){
        isExploded = true;
        var tileLocation = Map.getNearestTileLocation(location);
        return new Explosion(tileLocation, owner.getExplosionRadius(), map);
    }
}
