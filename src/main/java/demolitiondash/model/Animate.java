package demolitiondash.model;

import demolitiondash.model.bomb.Bomb;
import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

import java.util.List;
import java.util.Random;

public abstract class Animate extends Entity {
    protected Map map;
    protected Direction direction;
    protected Coordinate velocity = new Coordinate(0,0);
    protected  int speed;
    protected boolean isAlive;
    protected boolean isGhost;

    /**
     * Constructs an Animate object with the specified map, location, size, and speed.
     *
     * @param map      The map associated with the animate entity.
     * @param location The initial location of the animate entity.
     * @param size     The size of the animate entity.
     * @param speed    The speed of movement.
     */
    public Animate(Map map, Coordinate location, Size size, int speed) {
        super(location, size);
        this.isAlive = true;
        this.map = map;
        this.speed = speed;
        this.direction = Direction.getRandom();
        this.isGhost = false;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Map getMap() {
        return map;
    }

    /**
     * Checks if the animate entity is alive.
     *
     * @return True if the animate entity is alive, otherwise false.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Marks the animate entity as dead.
     */
    public void die(){
        isAlive = false;
    }

    /**
     * Checks if the animate entity is ready to be disposed.
     *
     * @return True if the animate entity is not alive, otherwise false.
     */
    @Override
    public boolean isReadyToGetDisposed() {
        return !isAlive;
    }

    public Coordinate getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinate velocity) {
        this.velocity = velocity;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }

    protected List<Bomb> bombsUnderAnimate;
    protected boolean collided = false;

    /**
     * Updates the animate entity's position and checks for collisions.
     */
    public void update() {
        bombsUnderAnimate = map.getStaticElems().stream()
                .filter(e -> e instanceof Bomb bomb
                        && bomb.isCollidingWith(this))
                .map(e -> (Bomb) e).toList();

        var oldLocation = this.location;

        Coordinate newVertLocation = new Coordinate(this.location.x, this.location.y + this.velocity.y);
        this.location = newVertLocation;

        handleMapEdgeCollisionVert();
        if(!isGhost) handleCollisionVert();

        Coordinate newHorizLocation = new Coordinate(this.location.x + this.velocity.x, this.location.y);
        this.location = newHorizLocation;

        handleMapEdgeCollisionHoriz();
        if(!isGhost) handleCollisionHoriz();

        if(this.location.equals(oldLocation.add(velocity))) collided = false;
    }

    public boolean isGhost() {
        return isGhost;
    }

    public void setGhost(boolean ghost) {
        isGhost = ghost;
    }

    /**
     * Checks if the animate entity has collided with any obstacles.
     *
     * @return True if the animate entity has collided, otherwise false.
     */
    public boolean hasCollided() {
        return collided;
    }

    protected boolean shouldFixCollision(Entity ent){
        return this.isCollidingWith(ent) && (!(ent instanceof Bomb bomb) || !bombsUnderAnimate.contains(bomb));
    }

    private void handleMapEdgeCollisionVert(){
        if(!map.includes(this.getHitbox())){
            collided = true;
            var hitBox = getHitbox();
            if (this.velocity.y < 0) {
                int diff = -hitBox.location.y;
                this.location = new Coordinate(this.location.x, this.location.y + diff);
            } else if (this.velocity.y > 0) {
                int diff = hitBox.location.y + hitBox.size.height - map.size.height;
                this.location = new Coordinate(this.location.x, this.location.y - diff);
            }
        }
    }

    private void handleCollisionVert() {
        for(var ent: map.getStaticElems()){
            if(shouldFixCollision(ent)){
                this.collided = true;
                var hitBox = getHitbox();
                if (this.velocity.y < 0) {
                    int diff = ent.location.y + ent.size.height - hitBox.location.y;
                    this.location = new Coordinate(this.location.x, this.location.y + diff);
                } else if (this.velocity.y > 0) {
                    int diff = hitBox.location.y + hitBox.size.height - ent.location.y;
                    this.location = new Coordinate(this.location.x, this.location.y - diff);
                }
            }
        }
    }

    private void handleMapEdgeCollisionHoriz(){
        if(!map.includes(this.getHitbox())){
            collided = true;
            var hitBox = getHitbox();
            if (this.velocity.x < 0) {
                int diff = -hitBox.location.x;
                this.location = new Coordinate(this.location.x - diff, this.location.y);
            } else if (this.velocity.x > 0) {
                int diff = hitBox.location.x + hitBox.size.width - map.size.width;
                this.location = new Coordinate(this.location.x - diff, this.location.y);
            }
        }
    }

    private void handleCollisionHoriz() {
        for(var ent: map.getStaticElems()) {
            if(shouldFixCollision(ent)){
                this.collided = true;
                var hitBox = getHitbox();
                if (this.velocity.x < 0) {
                    int diff = ent.location.x + ent.size.width - hitBox.location.x;
                    this.location = new Coordinate(this.location.x + diff, this.location.y);
                } else if (this.velocity.x > 0) {
                    int diff = hitBox.location.x + hitBox.size.width - ent.location.x;
                    this.location = new Coordinate(this.location.x - diff, this.location.y);
                }
            }
        }
    }
}
