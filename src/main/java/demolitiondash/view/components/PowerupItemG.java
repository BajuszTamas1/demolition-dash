package demolitiondash.view.components;

import demolitiondash.model.powerup.*;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Disposable;
import demolitiondash.util.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerupItemG implements Drawable, Disposable {
    private final PowerupItem powerupItem;
    private final Sprite anim;

    private static final Image bombup = ResourceLoader.loadImage("imgs/assets/Items/Pickups/bombup.jpg");
    private static final Image blastradius = ResourceLoader.loadImage("imgs/assets/Items/Pickups/blastradius.jpg");
    private static final Image invincibility = ResourceLoader.loadImage("imgs/assets/Items/Pickups/invincibility.jpg");
    private static final Image detonator = ResourceLoader.loadImage("imgs/assets/Items/Pickups/detonator.jpg");
    private static final Image rollerblade = ResourceLoader.loadImage("imgs/assets/Items/Pickups/rollerblade.jpg");
    private static final Image ghost = ResourceLoader.loadImage("imgs/assets/Items/Pickups/ghost.jpg");
    private static final Image barricade = ResourceLoader.loadImage("imgs/assets/Items/Pickups/barricade.png");

    public PowerupItemG(PowerupItem powerupItem){
        this.powerupItem = powerupItem;
        this.anim = getSprite();
    }

    private Sprite getSprite(){
        var powerup = powerupItem.getPowerup();
        if(powerup instanceof ExtraBombPowerup)
            return new Sprite(ImageUtils.toBufferedImage(bombup), 1, 0);
        if(powerup instanceof IncreaseBombRadiusPowerup)
            return new Sprite(ImageUtils.toBufferedImage(blastradius), 1, 0);
        if(powerup instanceof InvincibilityPowerup)
            return new Sprite(ImageUtils.toBufferedImage(invincibility), 1, 0);
        if(powerup instanceof RemoteBombsPowerup)
            return new Sprite(ImageUtils.toBufferedImage(detonator), 1, 0);
        if(powerup instanceof SpeedIncreasePowerup)
            return new Sprite(ImageUtils.toBufferedImage(rollerblade), 1, 0);
        if(powerup instanceof GhostPowerup)
            return new Sprite(ImageUtils.toBufferedImage(ghost), 1, 0);
        if(powerup instanceof BarricadePowerup)
            return new Sprite(ImageUtils.toBufferedImage(barricade), 1, 0);

        return new Sprite(ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Pickups/scroll.png")),4, 8);
    }

    public PowerupItem getPowerupItem() {
        return powerupItem;
    }

    @Override
    public BufferedImage getImage() {
        return anim.getCurrentFrame();
    }

    @Override
    public Coordinate getLocation() {
        return powerupItem.getLocation();
    }

    @Override
    public boolean isReadyToGetDisposed() {
        return powerupItem.isReadyToGetDisposed();
    }
}
