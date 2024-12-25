package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.model.powerup.*;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;
import java.util.Optional;
import java.util.Random;

public class Box extends Entity{
    private static final double ITEM_SPAWNING_PROBABILITY = 0.45;
    protected boolean isBroken;
    private PowerupItem powerupItem;
    public Box(Coordinate location) {
        super(location, new Size(Map.TILE_SIZE, Map.TILE_SIZE));
    }

    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Breaks the box and potentially spawns a powerup item.
     *
     * @return An optional containing the powerup item if spawned, otherwise empty.
     * @throws UnsupportedOperationException if the box is already broken.
     */
    public Optional<PowerupItem> breakBox(){
        if(isBroken){
            throw new UnsupportedOperationException("Box is already broken");
        }

        isBroken = true;
        Random r = new Random();
        if(r.nextDouble() < ITEM_SPAWNING_PROBABILITY){
            Powerup[] powerups = {new ExtraBombPowerup(), new IncreaseBombRadiusPowerup(), new InvincibilityPowerup(),
                    new InvincibilityPowerup(), new RemoteBombsPowerup(), new SpeedIncreasePowerup(), new BarricadePowerup(), new GhostPowerup()};
            int index = r.nextInt(powerups.length);
            return Optional.of(new PowerupItem(powerups[index], this.location));
        }
        return Optional.empty();
    }

    /**
     * Checks if the box is ready to be disposed (broken).
     *
     * @return True if the box is broken, otherwise false.
     */
    @Override
    public boolean isReadyToGetDisposed() {
        return isBroken;
    }
}
