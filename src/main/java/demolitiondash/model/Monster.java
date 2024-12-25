package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Animate {
    private static final Size MONSTER_SIZE = new Size(Map.TILE_SIZE, Map.TILE_SIZE);
    private static final int MONSTER_SPEED = 1;

    private final Random random;
    private int stepCount;

    public Monster(Map map) {
        super(map, new Coordinate(0, 0), MONSTER_SIZE, MONSTER_SPEED);
        setVelocity(this.direction.toVelocityVector(MONSTER_SPEED));
        random = new Random();
        stepCount = 0;
        if(map != null)
            map.addMonster(this);
    }

    @Override
    public void update(){
        super.update();
        changeDirection();
        stepCount++;
    }

    @Override
    public Boundary getHitbox() {
        var offsetY = Map.TILE_SIZE / 4;
        var size = new Size(Map.TILE_SIZE/2, Map.TILE_SIZE - offsetY);
        var offsetX = (this.size.width - size.width)/2;
        var location = new Coordinate(this.location.x + offsetX, this.location.y + offsetY);

        return new Boundary(location, size);
    }

    private boolean shouldChangeRandomly(){
        final int interval = random.nextInt(70, 200);
        if(stepCount % interval == 0){
            stepCount = 1;
            return random.nextDouble() < 0.5;
        }
        return false;
    }

    private void changeDirection() {
        if (!isAlive)
            return;

        if (collided || shouldChangeRandomly()) {
            ArrayList<Direction> directions = new ArrayList<>();
            for (Direction d : Direction.values()) {
                var newLocation = location.add(d.toVelocityVector(speed));
                this.setLocation(newLocation);

                if (map.getStaticElems().stream().noneMatch(this::shouldFixCollision)) {
                    directions.add(d);
                }
            }

            if (!directions.isEmpty()) {
                Direction selectedDirection = directions.get(random.nextInt(directions.size()));
                setDirection(selectedDirection);
                setVelocity(selectedDirection.toVelocityVector(speed));
            }
        }
    }
}
