package demolitiondash.view.components;

import demolitiondash.model.Flame;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.Coordinate;
import demolitiondash.util.ImageUtils;

import java.awt.image.BufferedImage;

public class FlameG implements Drawable{
    private static final BufferedImage spriteSheet =
        ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Explosions/bk_explo_short.png"));
    private static final Sprite anim = new Sprite(spriteSheet, 3, 16);
    private final Flame flame;

    public FlameG(Flame flame){
        this.flame = flame;
    }

    public Flame getFlame() {
        return flame;
    }

    @Override
    public BufferedImage getImage() {
        return anim.getCurrentFrame();
    }

    @Override
    public Coordinate getLocation() {
        return flame.getLocation();
    }
}
