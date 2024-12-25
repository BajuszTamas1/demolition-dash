package demolitiondash.view.components;

import demolitiondash.model.Box;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.Coordinate;
import demolitiondash.util.ImageUtils;

import java.awt.image.BufferedImage;
import java.time.Instant;

public class BoxG implements Drawable{
    private final Box box;

    private Sprite idleAnim;
    private Sprite breakAnim;

    private Instant disposeStamp;
    private final int disposeTimeMs = 500;

    public BoxG(Box box) {
        this.box = box;
        this.idleAnim = getIdleAnim();
        this.breakAnim = getBreakAnim();
    }

    public Box getBox() {
        return box;
    }

    protected Sprite getIdleAnim(){
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Box/Idle.png"));
        var sprite = new Sprite(buffImg, 1, 0);

        return sprite;
    }

    protected Sprite getBreakAnim(){
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Box/Hit (28x24).png"));
        var sprite = new Sprite(buffImg, 3, 8);

        return sprite;
    }

    @Override
    public BufferedImage getImage() {
        var res = idleAnim.getCurrentFrame();
        if(box.isBroken()){
            if(disposeStamp == null) disposeStamp = Instant.now().plusMillis(disposeTimeMs);
            res = breakAnim.getCurrentFrame();
        }

        return res;
    }

    @Override
    public Coordinate getLocation() {
        return box.getLocation();
    }

    public boolean isReadyToGetDisposed(){
        return disposeStamp != null && Instant.now().isAfter(disposeStamp);
    }
}
