package demolitiondash.model.bomb;

import demolitiondash.model.Player;

public interface BombFactory {
    /**
     * Creates a bomb object with the specified player as the owner.
     *
     * @param player The player who owns the bomb.
     * @return The created bomb object.
     */
    Bomb createBomb(Player player);
}
