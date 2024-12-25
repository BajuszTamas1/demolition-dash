package demolitiondash.view.components;

import demolitiondash.model.Direction;
import demolitiondash.model.Player;
import demolitiondash.model.powerup.GhostPowerup;
import demolitiondash.model.powerup.InvincibilityPowerup;
import demolitiondash.model.powerup.Powerup;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.ImageUtils;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.HashMap;
import java.util.Random;

public class PlayerG extends AnimateG {

    public boolean showBarricadeOutline;
    private boolean flickering = false;
    private final int flickerIntervMs = 300;
    private Instant nextFlickerUpdate;

    public PlayerG(Player player, double hueOffset) {
        super(player, hueOffset);
        this.showBarricadeOutline = false;
        this.nextFlickerUpdate = Instant.now();
    }

    public Player getPlayer(){
        return (Player) animate;
    }

    @Override
    public BufferedImage getImage() {
        var res =  super.getImage();
        var now  = Instant.now();
        var showInvincible = getPlayer().isInvincible();
        var soonExpiredPowerups = getPlayer().getPowerups().stream().filter(Powerup::isExpiring).toList();

        if(now.isAfter(nextFlickerUpdate) && !soonExpiredPowerups.isEmpty()){
            nextFlickerUpdate = now.plusMillis(flickerIntervMs);
            flickering = !flickering;
        }
        if(flickering){
            for(Powerup p: soonExpiredPowerups){
                if(p instanceof GhostPowerup) res = ImageUtils.applyOpacity(res, 0.4f);
                else if(p instanceof InvincibilityPowerup) showInvincible = false;
            }
            flickering = !soonExpiredPowerups.isEmpty();
        }
        if(showInvincible) res = ImageUtils.applyGlow(res, 0.5);

        return res;
    }

    @Override
    public HashMap<Direction, Sprite> getDieAnims(){
        var anims = new HashMap<Direction, Sprite>();
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Pink Man/Die (32x32).png"));
        buffImg = ImageUtils.adjustHue(buffImg, hueOffset);
        var sprite = new Sprite(buffImg, 6,20, false);

        anims.put(Direction.UP, sprite);
        anims.put(Direction.DOWN, sprite);
        anims.put(Direction.LEFT, sprite.flip());
        anims.put(Direction.RIGHT, sprite);

        return anims;
    }

    @Override
    public HashMap<Direction, Sprite> getWalkAnims(){
        var anims = new HashMap<Direction, Sprite>();
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Pink Man/Run (32x32).png"));
        buffImg = ImageUtils.adjustHue(buffImg, hueOffset);
        var sprite = new Sprite(buffImg, 12,20);

        anims.put(Direction.UP, sprite);
        anims.put(Direction.DOWN, sprite);
        anims.put(Direction.LEFT, sprite.flip());
        anims.put(Direction.RIGHT, sprite);

        return anims;
    }

    @Override
    public HashMap<Direction, Sprite> getIdleAnims(){
        var anims = new HashMap<Direction, Sprite>();
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Pink Man/Idle (32x32).png"));
        buffImg = ImageUtils.adjustHue(buffImg, hueOffset);
        var sprite = new Sprite(buffImg, 11,20);

        anims.put(Direction.UP, sprite);
        anims.put(Direction.DOWN, sprite);
        anims.put(Direction.LEFT, sprite.flip());
        anims.put(Direction.RIGHT, sprite);

        return anims;
    }
}
