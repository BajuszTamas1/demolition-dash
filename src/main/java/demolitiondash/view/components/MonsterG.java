package demolitiondash.view.components;

import demolitiondash.model.Direction;
import demolitiondash.model.Monster;
import demolitiondash.res.ResourceLoader;
import demolitiondash.util.ImageUtils;
import java.util.HashMap;

public class MonsterG extends AnimateG {
    public MonsterG(Monster monster) {
        super(monster);
    }

    @Override
    public HashMap<Direction, Sprite> getDieAnims(){
        var anims = new HashMap<Direction, Sprite>();
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Mask Dude/Die (32x32).png"));
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
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Mask Dude/Run (32x32).png"));
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
        var buffImg = ImageUtils.toBufferedImage(ResourceLoader.loadImage("imgs/assets/Characters/Mask Dude/Idle (32x32).png"));
        var sprite = new Sprite(buffImg, 11,20);

        anims.put(Direction.UP, sprite);
        anims.put(Direction.DOWN, sprite);
        anims.put(Direction.LEFT, sprite.flip());
        anims.put(Direction.RIGHT, sprite);

        return anims;
    }
}
