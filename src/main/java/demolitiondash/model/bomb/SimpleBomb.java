package demolitiondash.model.bomb;

import demolitiondash.model.Player;
import demolitiondash.util.Coordinate;

import java.time.LocalDateTime;


public class SimpleBomb extends Bomb{
    private final LocalDateTime boomTime = LocalDateTime.now().plusSeconds(3);
    public SimpleBomb(Coordinate location, Player owner) {
        super(location, owner);
    }

    @Override
    public boolean isReadyToExplode() {
        return LocalDateTime.now().isAfter(boomTime);
    }
}
