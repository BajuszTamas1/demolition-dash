package demolitiondash.model.bomb;

import demolitiondash.model.Player;
import demolitiondash.util.Coordinate;

public class RemoteBomb extends Bomb {
    private boolean readyToExplode;

    public void setReadyToExplode(boolean readyToExplode) {
        this.readyToExplode = readyToExplode;
    }

    @Override
    public boolean isReadyToExplode() {
        return readyToExplode;
    }

    public RemoteBomb(Coordinate location, Player owner) {
        super(location, owner);
    }
}
