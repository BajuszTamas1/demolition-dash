package demolitiondash.model.powerup;

import demolitiondash.model.Player;
import demolitiondash.model.powerup.pickupBehaviour.PowerupPickupBehaviour;

import java.time.Instant;

public abstract class Powerup {
    protected int durantionMs;
    protected Instant endTime;
    protected Player player;
    protected PowerupPickupBehaviour pickupBehaviour;

    protected Powerup(PowerupPickupBehaviour powerupPickupBehaviour){
        this.pickupBehaviour = powerupPickupBehaviour;
        durantionMs = 0;
    }

    protected Powerup(PowerupPickupBehaviour powerupPickupBehaviour, int durantionMs){
        this.pickupBehaviour = powerupPickupBehaviour;
        this.durantionMs = durantionMs;
    }

    /**
     * Activates the powerup for a player.
     *
     * @param player The player to activate the powerup for.
     */
    public void activate(Player player){
        this.pickupBehaviour.activate(this, player);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Applies the effect of the powerup.
     */
    public void effect(){
        if(durantionMs != 0)
            endTime = Instant.now().plusMillis(durantionMs);
    }

    /**
     * Clears any effects of the powerup.
     */
    public abstract void clear();

    /**
     * Checks if the powerup has expired.
     *
     * @return True if the powerup has expired, otherwise false.
     */
    public boolean hasExpired(){
        if(endTime == null)
            return false;

        return endTime.isBefore(Instant.now());
    }

    /**
     * Checks if the powerup is expiring soon.
     *
     * @return True if the powerup is expiring soon, otherwise false.
     */
    public boolean isExpiring(){
        return false;
    }
}
