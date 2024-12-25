package demolitiondash.model.powerup;

import demolitiondash.model.bomb.RemoteBombFactory;
import demolitiondash.model.powerup.pickupBehaviour.IgnoreWhenDuplicate;

public class RemoteBombsPowerup extends Powerup {
    public RemoteBombsPowerup() {
        super(IgnoreWhenDuplicate.getInstance());
    }

    /**
     * Applies the effect of enabling remote bombs.
     * Sets the bomb factory of the player to create remote bombs.
     */
    @Override
    public void effect() {
        super.effect();
        player.setBombFactory(RemoteBombFactory.getInstance());
    }

    @Override
    public void clear() {}
}
