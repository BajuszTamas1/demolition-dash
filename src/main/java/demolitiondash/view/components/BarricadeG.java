package demolitiondash.view.components;

import demolitiondash.model.Barricade;
import demolitiondash.model.Box;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.ImageUtils;

public class BarricadeG extends BoxG{
    public BarricadeG(Barricade barr) {
        super(barr);
    }

    @Override
    protected Sprite getIdleAnim(){
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Barricade/Idle.png"));
        var sprite = new Sprite(buffImg, 1, 0);

        return sprite;
    }

    @Override
    protected Sprite getBreakAnim(){
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Items/Barricade/Hit (28x24).png"));
        var sprite = new Sprite(buffImg, 2, 8);

        return sprite;
    }
}
