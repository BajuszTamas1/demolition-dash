package demolitiondash.model.powerup;

import demolitiondash.model.powerup.pickupBehaviour.StackWhenDuplicate;

public class ExtraBombPowerup extends Powerup
{
    public ExtraBombPowerup() {
        super(StackWhenDuplicate.getInstance());
    }

    /**
     * Applies the effect of the extra bomb powerup.
     * Increases the maximum bomb count of the player by 1.
     */
    @Override
    public void effect() {
        super.effect();
        int c = player.getMaxBombCount() + 1;
        player.setMaxBombCount(c);
    }

    @Override
    public void clear() {}
}
