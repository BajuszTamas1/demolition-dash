package demolitiondash.util.player;

/**
 * This class represents an entry in the player store.
 * Each entry consists of a player's name and hue.
 */
public class PlayerStoreEntry {
    private String playerName;  // The name of the player
    private Double playerHue;   // The hue of the player

    /**
     * Constructs a new PlayerStoreEntry with the specified name and hue.
     *
     * @param playerName the name of the player
     * @param playerHue the hue of the player
     */
    public PlayerStoreEntry(String playerName, Double playerHue) {
        this.playerName = playerName;
        this.playerHue = playerHue;
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player.
     *
     * @param playerName the new name of the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Returns the hue of the player.
     *
     * @return the hue of the player
     */
    public Double getPlayerHue() {
        return playerHue;
    }

    /**
     * Sets the hue of the player.
     *
     * @param playerHue the new hue of the player
     */
    public void setPlayerHue(Double playerHue) {
        this.playerHue = playerHue;
    }

    /**
     * Returns a string representation of the player store entry.
     * The string representation consists of the player's name and hue, separated by a comma.
     *
     * @return a string representation of the player store entry
     */
    public String stringify() {
        return playerName + "," + playerHue;
    }
}