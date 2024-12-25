package demolitiondash.model.map;

import demolitiondash.model.*;
import demolitiondash.model.bomb.Bomb;
import demolitiondash.model.bomb.RemoteBomb;
import demolitiondash.model.powerup.PowerupItem;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class Map extends Entity{
    /**
     * The size of each tile in the map.
     */
    public static int TILE_SIZE = 40;
    public final int ROW_COUNT;
    public final int COL_COUNT;
    private ArrayList<Entity> staticElems = new ArrayList<>();
    private final Queue<Entity> staticElemsToAdd = new ConcurrentLinkedQueue<>();
    private ArrayList<Animate> animates = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private ArrayList<PowerupItem> items = new ArrayList<>();
    private final MapLayout layout;

    /**
     * Constructs a new Map object with the specified layout.
     *
     * @param layout The layout of the map.
     */
    public Map(MapLayout layout){
        super(new Coordinate(0,0), new Size(layout.COL_COUNT * TILE_SIZE, layout.ROW_COUNT * TILE_SIZE));
        this.layout = layout;
        this.ROW_COUNT = layout.ROW_COUNT;
        this.COL_COUNT = layout.COL_COUNT;
        this.staticElems = layout.getStatics();
    }

    private final List<MapObserver> observers = new ArrayList<>();

    public void addObserver(MapObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MapObserver observer) {
        observers.remove(observer);
    }

    public void notifyBombPlaced(Bomb bomb) {
        for (MapObserver observer : observers) {
            observer.bombAdded(bomb);
        }
    }

    public void notifyBoxAdded(Box box) {
        for (MapObserver observer : observers) {
            observer.boxAdded(box);
        }
    }

    private List<Flame> getFlames(){
        var allFlames = explosions.stream().flatMap(e -> e.getFlames().stream()).toList();
        return allFlames;
    }

    public void notifyFlameUpdate(){
        for (MapObserver observer : observers) {
            observer.flamesUpdated(getFlames());
        }
    }

    public void notifyPowerupItemAdded(PowerupItem powerupItem) {
        for (MapObserver observer : observers) {
            observer.powerupItemAdded(powerupItem);
        }
    }

    public void notifyBarricadeOutlineAdded(Player player){
        for(MapObserver observer : observers){
            observer.barricadeOutlineAdded(player);
        }
    }

    public void notifyBarricadeOutlineRemoved(Player player){
        for(MapObserver observer : observers){
            observer.barricadeOutlineRemoved(player);
        }
    }

    public MapLayout getLayout() {
        return layout;
    }

    public int getBombCount(){
        return staticElemCount(e -> e instanceof Bomb);
    }

    public List<Animate> getAnimates(){
        return animates;
    }

    /**
     * Converts a coordinate value to an index.
     *
     * @param c The coordinate value.
     * @return The corresponding index.
     */
    public static int coordinateValueToIndex(int c){
        return c / TILE_SIZE;
    }

    public int getWidth(){
        return this.size.width;
    }

    public int getHeight(){
        return this.size.height;
    }

    public ArrayList<Entity> getStaticElems() {
        return staticElems;
    }

    /**
     * Adds a player to the map.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player){
        var playerCount = getPlayers().size();
        if(playerCount > layout.getPlayerPositions().size())
            throw new UnsupportedOperationException("Cannot add more players to the map.");
        player.setMap(this);
        player.setLocation(layout.getPlayerPositions().get(playerCount));

        animates.add(player);
    }

    /**
     * Adds a monster to the map.
     *
     * @param monster The monster to add.
     */
    public void addMonster(Monster monster){
        var monsterCount = getMonsters().size();
        if(monsterCount > layout.getMonsterPositions().size())
            throw new UnsupportedOperationException("Cannot add more players to the map.");
        monster.setLocation(layout.getMonsterPositions().get(monsterCount));

        animates.add(monster);
    }

    public List<Player> getPlayers(){
        return animates.stream().filter(a -> a instanceof Player).map(a -> (Player) a).toList();
    }

    public List<Monster> getMonsters(){
        return animates.stream().filter(a -> a instanceof Monster).map(a -> (Monster) a).toList();
    }

    /**
     * Checks if a coordinate is valid on the map.
     *
     * @param coord The coordinate to check.
     * @return True if the coordinate is valid, otherwise false.
     */
    public boolean isValidCoordinate(Coordinate coord){
        return coord.x >= 0 && coord.y >= 0
                && coord.x <= getWidth() && coord.y <= getHeight();
    }

    /**
     * Gets the nearest tile location to a given coordinate.
     *
     * @param location The coordinate.
     * @return The nearest tile location.
     */
    public static Coordinate getNearestTileLocation(Coordinate location) {
        int xTile = (location.x / TILE_SIZE) * TILE_SIZE;
        int yTile = (location.y / TILE_SIZE) * TILE_SIZE;
        return new Coordinate(xTile, yTile);
    }

    public List<Player> alivePlayers(){
        return animates.stream().filter(a -> a instanceof Player player && player.isAlive()).map(a -> (Player) a).toList();
    }
    public int alivePlayerCount(){
        return alivePlayers().size();
    }

    public int staticElemCount(Predicate<Entity> pred){
        return (int) staticElems.stream().filter(pred).count();
    }

    public void explodeRemoteBombs(Player player) {
        staticElems.stream()
                .filter(s -> s instanceof RemoteBomb b && b.getOwner() == player)
                .forEach(b -> ((RemoteBomb) b).setReadyToExplode(true));
    }

    public int getExplosionCount(){
        return explosions.size();
    }

    /**
     * Gets the count of bombs owned by a player on the map.
     *
     * @param player The player.
     * @return The count of bombs owned by the player.
     */
    public int getBombCount(Player player){
        return staticElemCount(e -> e instanceof Bomb bomb && bomb.getOwner().equals(player));
    }

    /**
     * Gets the count of barricades owned by a player on the map.
     *
     * @param player The player.
     * @return The count of barricades owned by the player.
     */
    public int getBarricadeCount(Player player){
        return staticElemCount(e -> e instanceof Barricade barr
                && barr.getOwner().isPresent()
                && barr.getOwner().get().equals(player));
    }

    /**
     * Gets the count of bombs owned by a player based on the player number.
     *
     * @param playerNo The player number.
     * @return The count of bombs owned by the player.
     */
    public int getBombCount(int playerNo){
        Optional<Player> player = getPlayerByNumber(playerNo);

        int bombCount = 0;
        if(player.isPresent()) bombCount = getBombCount(player.get());
        return bombCount;
    }

    /**
     * Tries to place a bomb on the map.
     *
     * @param bomb The bomb to place.
     * @return True if the bomb was placed successfully, otherwise false.
     */
    public boolean tryPlacebomb(Bomb bomb){
        if(bomb.isCollidingWithAny(staticElems)) return false;

        staticElemsToAdd.add(bomb);
        notifyBombPlaced(bomb);
        return true;
    }

    private Optional<Player> getPlayerByNumber(int playerNo){
        return getPlayers().stream().filter((e) -> e.getPlayerNumber() == playerNo).findAny();
    }

    public void handlePlayerMovement(int playerNo, List<Direction> d){
       getPlayerByNumber(playerNo).ifPresent(p -> p.move(d));
    }

    public void handlePlayerBombPlacement(int playerNo){
        getPlayerByNumber(playerNo).ifPresent(Player::tryPlaceBomb);
    }

    public void handleBaricadeOutline(int playerNo){
        getPlayerByNumber(playerNo).ifPresent(this::notifyBarricadeOutlineAdded);
    }

    public void handleBarricadePlacement(int playerNo){
        getPlayerByNumber(playerNo).ifPresent(p -> {
            notifyBarricadeOutlineRemoved(p);

            var barrLocation = getBarricadeLocation(p);
            if(canPlaceBarricade(p, barrLocation)){
                var barr = new Barricade(barrLocation, p);
                staticElemsToAdd.add(barr);
                notifyBoxAdded(barr);
            }
        });
    }

    /**
     * Gets the location for barricade placement by a player.
     *
     * @param player The player.
     * @return The location for barricade placement.
     */
    public Coordinate getBarricadeLocation(Player player){
        var tileLocation = Map.getNearestTileLocation(player.getCenter());

        return tileLocation.add(player.getDirection().toVelocityVector(Map.TILE_SIZE));
    }

    private final Barricade dummyBarricade = new Barricade(new Coordinate(0,0));
    /**
     * Checks if a barricade can be placed by a player at a specific coordinate.
     *
     * @param player     The player.
     * @param coordinate The coordinate.
     * @return True if the barricade can be placed, otherwise false.
     */
    public boolean canPlaceBarricade(Player player, Coordinate coordinate){
        dummyBarricade.setLocation(coordinate);
        return player.canPlaceBarricade() && !dummyBarricade.isCollidingWithAny(staticElems) &&
                !dummyBarricade.isCollidingWithAny(List.copyOf(animates));
    }


    private void updateAnimates(){
        List<Entity> flames = List.copyOf(getFlames());
        List<Entity> monsters = List.copyOf(getMonsters());
        var disposables = new ArrayList<Animate>();

        for(Animate anim: animates){
            if(anim.isReadyToGetDisposed()){
                disposables.add(anim);
                continue;
            }

            anim.update();
            if(anim.isCollidingWithAny(flames)
                || (anim instanceof Player p && p.isCollidingWithAny(monsters))
            ) anim.die();
        }

        for(var disp: disposables){
            animates.remove(disp);
        }
    }

    private void updateStaticElems(){
        var disposables = new ArrayList<Entity>();
        List<Entity> flames = List.copyOf(getFlames());

        for(var elem: staticElems){
            if(elem.isReadyToGetDisposed()){
                disposables.add(elem);
                continue;
            }
            if(elem instanceof Bomb bomb)
                updateBomb(bomb, flames);
            if(elem instanceof Box box)
                updateBox(box, flames);
        }
        for(var disp: disposables){
            staticElems.remove(disp);
        }
    }

    private void checkPowerupCollision(){
        ArrayList<PowerupItem> disposables = new ArrayList<>();
        for(PowerupItem item: items){
            var player = getPlayers().stream().filter(p -> p.isCollidingWith(item)).findAny();
            player.ifPresent(p -> {
                item.pickUp(p);
                disposables.add(item);
            });
        }
        for(PowerupItem item: disposables){
            items.remove(item);
        }
    }

    private void updateBomb(Bomb bomb, List<Entity> flames){
        if(bomb.isCollidingWithAny(flames) || bomb.isReadyToExplode()){
            var explosion = bomb.explode(this);
            explosions.add(explosion);
        }
    }

    private void updateBox(Box box, List<Entity> flames){
        if(box.isCollidingWithAny(flames)){
            var item = box.breakBox();
            item.ifPresent(i -> {
                items.add(i);
                notifyPowerupItemAdded(i);
            });
        }
    }

    private void updateExplosions(){
        var disposables = new ArrayList<Explosion>();
        boolean expansion = false;

        for(var exp : explosions){
            exp.update();

            if(exp.isReadyToGetDisposed())
                disposables.add(exp);

            if(exp.hasExpanded())
                expansion = true;
        }

        for(var disp: disposables){
            explosions.remove(disp);
        }

        if(expansion || !disposables.isEmpty()){
            notifyFlameUpdate();
        }
    }

    private void addNewStaticElems(){
       synchronized (staticElemsToAdd){
           staticElems.addAll(staticElemsToAdd);
           staticElemsToAdd.clear();
       }
    }

    public void update(){
        updateStaticElems();
        updateAnimates();
        checkPowerupCollision();
        updateExplosions();
        addNewStaticElems();
    }

}
