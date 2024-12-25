package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

public class Wall extends Entity{
    public Wall(Coordinate coordinate){
        super(coordinate, new Size(Map.TILE_SIZE, Map.TILE_SIZE));
    }
}
