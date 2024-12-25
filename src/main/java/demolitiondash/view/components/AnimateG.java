package demolitiondash.view.components;

import demolitiondash.model.Animate;
import demolitiondash.model.Direction;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Disposable;
import demolitiondash.util.ImageUtils;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.HashMap;

public abstract class AnimateG implements Drawable, Disposable {
    protected HashMap<Direction, Sprite> walkAnims;
    protected HashMap<Direction, Sprite> idleAnims;
    protected HashMap<Direction, Sprite> dieAnims;
    protected final Animate animate;
    private Instant disposeStamp;
    private final int disposeTimeMs = 3_000;
    protected double hueOffset;


    public AnimateG(Animate animate, double hueOffset) {
        this.animate = animate;
        this.hueOffset = hueOffset;
        this.walkAnims = getWalkAnims();
        this.idleAnims = getIdleAnims();
        this.dieAnims = getIdleAnims();
        this.dieAnims = getDieAnims();
    }

    public AnimateG(Animate animate) {
        this(animate, 0);
    }

    public Animate getAnimate() {
        return animate;
    }

    @Override
    public BufferedImage getImage() {
        var dir = animate.getDirection();

        var res = idleAnims.get(dir).getCurrentFrame();
        if(!animate.isAlive()){
            if(disposeStamp == null) disposeStamp = Instant.now().plusMillis(disposeTimeMs);
            res = dieAnims.get(dir).getCurrentFrame();
        }
        else if(!animate.getVelocity().equals(new Coordinate(0,0))) res = walkAnims.get(dir).getCurrentFrame();

        if(animate.isGhost()) res = ImageUtils.applyOpacity(res, 0.65f);

        return res;
    }

    @Override
    public Coordinate getLocation() {
        return animate.getLocation();
    }

    public boolean isReadyToGetDisposed(){
        return disposeStamp != null && Instant.now().isAfter(disposeStamp);
    }

    public abstract HashMap<Direction, Sprite> getDieAnims();

    public abstract HashMap<Direction, Sprite> getWalkAnims();

    public abstract HashMap<Direction, Sprite> getIdleAnims();
}
