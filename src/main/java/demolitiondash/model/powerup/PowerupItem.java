package demolitiondash.model.powerup;

import demolitiondash.model.Entity;
import demolitiondash.model.Player;
import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

public class PowerupItem extends Entity {
    private Powerup powerup;
    private boolean isPickedUp;
    private final static Size SIZE = new Size(Map.TILE_SIZE, Map.TILE_SIZE);

    /**
     * Constructs a PowerupItem object with the specified powerup and location.
     *
     * @param powerup  The powerup associated with the item.
     * @param location The location of the powerup item.
     */
    public PowerupItem(Powerup powerup, Coordinate location) {
        super(location, SIZE);
        this.powerup = powerup;
        this.isPickedUp = false;
    }

    public Powerup getPowerup() {
        return powerup;
    }

    /**
     * Picks up the powerup item by a player.
     * Activates the associated powerup for the player.
     *
     * @param player The player who picks up the powerup item.
     */
    public void pickUp(Player player) {
        powerup.activate(player);
        isPickedUp = true;
    }


    @Override
    public boolean isReadyToGetDisposed() {
        return isPickedUp;
    }
}
