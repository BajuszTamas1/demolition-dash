package demolitiondash.view.components;

import demolitiondash.view.windows.GameSettingsView;

import javax.swing.*;

/**
 * This class represents a store for game data.
 * It provides methods for loading and saving game data to a file.
 */
public class GameStore {
    private static JPanel panel;
    private static GameStore instance;
    private GameSettingsView gameSettingsView;

    private GameStore() {
        gameSettingsView = GameSettingsView.getInstance();
        panel = gameSettingsView.getPanel();
    }

    /**
     * Returns the panel of the game store.
     * @return the panel
     */
    public static JPanel getPanel() {
        return panel;
    }

    /**
     * Returns the singleton instance of the game store.
     * If the instance is null, a new instance is created.
     * @return the singleton instance of the game store
     */
    public static GameStore getInstance() {
        if (instance == null) {
            instance = new GameStore();
        }
        return instance;
    }

}
