package demolitiondash.view.components;

import demolitiondash.model.bomb.Bomb;
import demolitiondash.model.bomb.RemoteBomb;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Disposable;
import demolitiondash.util.ImageUtils;

import java.awt.image.BufferedImage;

public class BombG implements Drawable, Disposable {
    private static final BufferedImage spriteSheet =
            ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Bomb/1-Bit Bomb Sprite Sheet.png"));
    private static final BufferedImage rcSpriteSheet =
            ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Bomb/rc_bomb.png"));
    private final Bomb bomb;

    private final Sprite anim;

    public BombG(Bomb bomb){

        this.bomb = bomb;
        if(bomb instanceof RemoteBomb)
            this.anim = new Sprite(rcSpriteSheet, 2, 2);
        else
            this.anim = new Sprite(spriteSheet, 3, 20);
    }

    public Bomb getBomb() {
        return bomb;
    }

    @Override
    public BufferedImage getImage() {
        return anim.getCurrentFrame();
    }

    @Override
    public Coordinate getLocation() {
        return bomb.getLocation();
    }

    @Override
    public boolean isReadyToGetDisposed() {
        return bomb.isReadyToGetDisposed();
    }
}
