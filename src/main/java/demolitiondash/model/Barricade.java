package demolitiondash.model;

import demolitiondash.model.powerup.PowerupItem;
import demolitiondash.util.Coordinate;

import java.util.Optional;

public class Barricade extends Box{
    private final Player owner;
    public Barricade(Coordinate location) {
        super(location);
        this.owner = null;
    }
    public Barricade(Coordinate location, Player owner) {
        super(location);
        this.owner = owner;
    }

    /**
     * Gets the owner of the barricade.
     *
     * @return An optional containing the owner of the barricade if it has one, otherwise empty.
     */
    public Optional<Player> getOwner() {
        if(owner == null) return Optional.empty();
        return Optional.of(owner);
    }

    /**
     * Attempts to break the barricade.
     *
     * @return An empty optional since a barricade cannot drop powerups.
     * @throws UnsupportedOperationException If the barricade is already broken.
     */
    @Override
    public Optional<PowerupItem> breakBox(){
        if(isBroken()) throw new UnsupportedOperationException("Barricade is alreay broken");
        else {
            this.isBroken = true;
            return Optional.empty();
        }
    }
}
