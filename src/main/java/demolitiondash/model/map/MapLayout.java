package demolitiondash.model.map;

import demolitiondash.model.Entity;
import demolitiondash.util.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;


public class MapLayout {
    private final ArrayList<Entity> statics;
    private final ArrayList<Coordinate> playerPositions;
    private final ArrayList<Coordinate> monsterPositions;

    public final int ROW_COUNT;
    public final int COL_COUNT;

    /**
     * Constructs a new MapLayout object with the specified parameters.
     *
     * @param statics          The list of static entities on the map layout.
     * @param rowCount         The number of rows in the map layout.
     * @param colCount         The number of columns in the map layout.
     * @param playerPositions  The list of positions for players on the map layout.
     * @param monsterPositions The list of positions for monsters on the map layout.
     */
    public MapLayout(ArrayList<Entity> statics, int rowCount, int colCount, ArrayList<Coordinate> playerPositions, ArrayList<Coordinate> monsterPositions){
        this.statics = statics;
        this.playerPositions = playerPositions;
        this.monsterPositions = monsterPositions;
        this.ROW_COUNT = rowCount;
        this.COL_COUNT = colCount;
    }


    public ArrayList<Coordinate> getPlayerPositions() {
        return playerPositions;
    }
    public ArrayList<Coordinate> getMonsterPositions() {
        return monsterPositions;
    }
    public ArrayList<Entity> getStatics() {
        return statics;
    }


    /**
     * Creates a deep copy of the map layout.
     *
     * @return A clone of the map layout.
     */
    public MapLayout clone(){
        ArrayList<Entity> staticsCopy = new ArrayList<>();
        for (Entity entity : statics) {
            staticsCopy.add(entity.clone());
        }

        ArrayList<Coordinate> playerPositionsCopy = new ArrayList<>();
        for (Coordinate coordinate : playerPositions) {
            playerPositionsCopy.add(coordinate.clone());
        }

        ArrayList<Coordinate> monsterPositionsCopy = new ArrayList<>();
        for (Coordinate coordinate : monsterPositions) {
            monsterPositionsCopy.add(coordinate.clone());
        }

        return new MapLayout(
                staticsCopy,
                ROW_COUNT, COL_COUNT,
                playerPositionsCopy,
                monsterPositionsCopy);
    }
}
