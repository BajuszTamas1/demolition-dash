package demolitiondash.model;

import demolitiondash.model.bomb.BombFactory;
import demolitiondash.model.bomb.RemoteBombFactory;
import demolitiondash.model.bomb.SimpleBombFactory;
import demolitiondash.model.map.Map;
import demolitiondash.model.powerup.Powerup;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

import java.util.ArrayList;
import java.util.List;

public class Player extends Animate{
    private int maxBombCount;
    private int maxBarricadeCount;
    private BombFactory bombFactory;
    private Map map;

    private final String name;
    public final ArrayList<Powerup> powerups = new ArrayList<>();
    private int explosionRadius;
    private final int playerNumber;

    private boolean invincible;

    public static final int PLAYER_DEFAULT_SPEED = 1;
    private static final Size PLAYER_SIZE = new Size(Map.TILE_SIZE, Map.TILE_SIZE);
    public Player(Map map, String name, int playerNumber) {
        super(map, new Coordinate(0,0), PLAYER_SIZE, PLAYER_DEFAULT_SPEED);
        this.name = name;
        this.explosionRadius = 2;
        this.maxBombCount = 1;
        this.bombFactory = SimpleBombFactory.getInstance();
        this.invincible = false;
        this.playerNumber = playerNumber;
        if(map != null)
            map.addPlayer(this);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getMaxBombCount() {
        return maxBombCount;
    }

    public void setMaxBombCount(int c) {
        this.maxBombCount = c;
    }

    public int getMaxBarrierCount() {
        return maxBarricadeCount;
    }

    public void setMaxBarrierCount(int maxBarrierCount) {
        this.maxBarricadeCount = maxBarrierCount;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }
    public void setExplosionRadius(int r){
        explosionRadius = r;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber(){ return playerNumber; }

    /**
     * Determines if the player can place a bomb.
     *
     * @return True if the player can place a bomb, otherwise false.
     */
    public boolean canPlaceBomb() {
        return maxBombCount > map.getBombCount(this);
    }

    public void setBombFactory(BombFactory bombFactory) {
        this.bombFactory = bombFactory;
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public void addPowerup(Powerup powerup) {
        powerup.setPlayer(this);
        powerup.effect();
        powerups.add(powerup);
    }

    private void updatePowerups(){
        var disposables = powerups.stream().filter(Powerup::hasExpired).toList();
        for(var disp : disposables){
            powerups.remove(disp);
            disp.clear();
        }
    }

    /**
     * Tries to place a bomb.
     *
     * @return True if the bomb was placed successfully, otherwise false.
     */
    public boolean tryPlaceBomb() {
        if(canPlaceBomb()){
            var bomb = bombFactory.createBomb(this);
            return map.tryPlacebomb(bomb);
        }

        if(bombFactory instanceof RemoteBombFactory){
            map.explodeRemoteBombs(this);
        }

        return false;
    }

    /**
     * Determines if the player can place a barricade.
     *
     * @return True if the player can place a barricade, otherwise false.
     */
    public boolean canPlaceBarricade(){
        return map.getBarricadeCount(this) < maxBarricadeCount;
    }

    @Override
    public Boundary getHitbox() {
        var offsetY = Map.TILE_SIZE / 4;
        var size = new Size(Map.TILE_SIZE/2, Map.TILE_SIZE - offsetY);
        var offsetX = (this.size.width - size.width)/2;
        var location = new Coordinate(this.location.x + offsetX, this.location.y + offsetY);

        return new Boundary(location, size);
    }

    /**
     * Moves the player in the specified directions.
     *
     * @param directions The list of directions to move the player.
     */
    public void move(List<Direction> directions) {
        Coordinate velocity = new Coordinate(0, 0);
        for (Direction d: directions) {
            Coordinate dirVel = d.toVelocityVector(speed);
            velocity = Coordinate.add(velocity, dirVel);
            this.direction = d;
        }
        setVelocity(velocity);
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isInvincible() {
        return invincible;
    }

    private void explodeRemoteBombsOnDeath(){
        if(bombFactory == RemoteBombFactory.getInstance()){
            map.explodeRemoteBombs(this);
        }
    }

    @Override
    public void die() {
        if(!invincible){
            explodeRemoteBombsOnDeath();
            super.die();
        }
    }

    public void forceDie(){
        explodeRemoteBombsOnDeath();
        super.die();
    }

    @Override
    public boolean isReadyToGetDisposed(){
        return !isAlive();
    }

    @Override
    public void update() {
        super.update();
        updatePowerups();
    }
}
