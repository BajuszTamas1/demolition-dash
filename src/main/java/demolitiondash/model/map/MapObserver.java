package demolitiondash.model.map;

import demolitiondash.model.*;
import demolitiondash.model.bomb.Bomb;
import demolitiondash.model.powerup.PowerupItem;

import java.util.List;

public interface MapObserver {
    void bombAdded(Bomb bomb);
    void flamesUpdated(List<Flame> flames);
    void powerupItemAdded(PowerupItem powerupItem);
    void barricadeOutlineAdded(Player player);
    void barricadeOutlineRemoved(Player player);
    void boxAdded(Box box);
}

