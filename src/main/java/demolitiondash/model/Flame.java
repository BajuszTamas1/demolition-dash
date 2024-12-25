package demolitiondash.model;

import demolitiondash.model.map.Map;
import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

public class Flame extends Entity{
    public Flame(Coordinate location) {
        super(location, new Size(Map.TILE_SIZE, Map.TILE_SIZE));
    }
}
