package demolitiondash.model;

import demolitiondash.model.bomb.Bomb;
import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Disposable;

import java.time.Instant;
import java.util.ArrayList;

public class Explosion implements Disposable {
    /**
     * The radius of the explosion counted in flames
     */
    private int currentRadius = 0;
    private final int maxRadius;
    private final ArrayList<Flame> flames = new ArrayList<>();
    private Instant lastExpanded;
    private int expandMs = 250;
    private final Coordinate location;
    private final Map map;
    private boolean expandedLastUpdate;
    private boolean timeToUpdate;

    private boolean isSpreadingLeft = true;
    private boolean isSpreadingRight = true;
    private boolean isSpreadingUp = true;
    private boolean isSpreadingDown = true;

    public Explosion(Coordinate location, int maxRadius, Map map) {
        this.location = Map.getNearestTileLocation(location);;
        this.maxRadius = maxRadius;
        this.map = map;
        this.timeToUpdate = true;
    }
    public Coordinate getLocation() {
        return location;
    }

    private void expand(){
        currentRadius += 1;
        addFlames();
    }


    private boolean canExpand(){
        return currentRadius < maxRadius
                && (isSpreadingRight
                || isSpreadingDown
                || isSpreadingUp
                || isSpreadingLeft);
    }

    private boolean trySpreadToLocation(Flame flame){
        var possCollisions = map.getStaticElems().stream().filter(e -> !(e instanceof Bomb)).toList();

        for(var ent : possCollisions){
            if(flame.isCollidingWith(ent)){
                if(ent instanceof Box){
                    flames.add(flame);
                    return false;
                }
                if(ent instanceof Wall) return false;
            }
        }
        flames.add(flame);
        return true;
    }

    private void addFlames(){
        if(this.currentRadius == 1){
            flames.add(new Flame(location));
            return;
        }

        var amountPlus = (currentRadius - 1) * Map.TILE_SIZE;
        var flameXPlus = new Flame(location.add(new Coordinate(amountPlus, 0)));
        var flameYPlus = new Flame(location.add(new Coordinate(0, amountPlus)));

        if(!(isSpreadingRight && trySpreadToLocation(flameXPlus))) isSpreadingRight = false;
        if(!(isSpreadingDown && trySpreadToLocation(flameYPlus))) isSpreadingDown = false;

        var amountMinus = -1 * amountPlus;
        var flameXMinus = new Flame(location.add(new Coordinate(amountMinus, 0)));
        var flameYMinus = new Flame(location.add(new Coordinate(0, amountMinus)));

        if(!(isSpreadingLeft && trySpreadToLocation(flameXMinus))) isSpreadingLeft = false;
        if(!(isSpreadingUp && trySpreadToLocation(flameYMinus))) isSpreadingUp = false;
    }

    public void update(){
        timeToUpdate = lastExpanded == null || Instant.now().isAfter(lastExpanded.plusMillis(expandMs));
        if(timeToUpdate && canExpand()){
            expand();
            lastExpanded = Instant.now();
            expandedLastUpdate = true;
        }
        else expandedLastUpdate = false;
    }

    @Override
    public boolean isReadyToGetDisposed() {
        return timeToUpdate && !canExpand() && !expandedLastUpdate;
    }

    /**
     * Checks if the explosion has expanded in the last update.
     *
     * @return True if the explosion has expanded in the last update, otherwise false.
     */
    public boolean hasExpanded(){ return expandedLastUpdate;}


    /**
     * Gets the list of flames produced by the explosion.
     *
     * @return The list of flames produced by the explosion.
     */
    public ArrayList<Flame> getFlames(){
        return flames;
    }
}
