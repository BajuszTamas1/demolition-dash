package demolitiondash.util.player;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a store for player data.
 * It provides methods for loading and saving player data to a file.
 */
public class PlayerStore {
    private static PlayerStore instance;  // Singleton instance of PlayerStore
    private final String PLAYERS_FILE_NAME = "players.txt";  // File name for storing player data
    private ArrayList<PlayerStoreEntry> entries = new ArrayList<>();  // List of player entries

    private PlayerStore() {
        loadFromFile();
    }

    private void loadFromFile() {
        entries.clear();
        try {
            BufferedInputStream playersIs = new BufferedInputStream(Files.newInputStream(Path.of(PLAYERS_FILE_NAME)));
            Scanner playersSc = new Scanner(playersIs);
            while (playersSc.hasNextLine()){
                String line = playersSc.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2){
                    String playerName = parts[0];
                    Double playerHue = Double.parseDouble(parts[1]);
                    entries.add(new PlayerStoreEntry(playerName, playerHue));
                }
            }
            System.out.println("Players: Loaded from file.");
        } catch (NoSuchFileException e) {
            System.out.println("Players: File does not exist, reverting to default.");
            resetToDefault();
        } catch (Exception e) {
            System.out.println("Error while loading players: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetToDefault() {
        System.out.println("Players: Setting default");
        entries.clear();
        entries.add(new PlayerStoreEntry("Player 1", 0.0));
        entries.add(new PlayerStoreEntry("Player 2", 0.5));
        entries.add(new PlayerStoreEntry("Player 3", 0.75));
    }

    /**
     * Sets player names.
     * If there are more names than existing entries, new entries are created.
     * If there are fewer names than existing entries, excess entries are removed.
     *
     * @param players the new player names
     */
    public void setPlayers(ArrayList<String> players){
        for (int i = 0; i < players.size(); i++) {
            if (i < entries.size()) {
                entries.get(i).setPlayerName(players.get(i));
            } else {
                entries.add(new PlayerStoreEntry(players.get(i), 0.0));
            }
        }
        while (entries.size() > players.size()) {
            entries.remove(entries.size() - 1);
        }
    }

    /**
     * Sets player hues.
     * If there are more hues than existing entries, new entries are created.
     * If there are fewer hues than existing entries, excess entries are removed.
     *
     * @param playerHues the new player hues
     */
    public void setPlayerHues(ArrayList<Double> playerHues){
        for (int i = 0; i < playerHues.size(); i++) {
            if (i < entries.size()) {
                entries.get(i).setPlayerHue(playerHues.get(i));
            } else {
                entries.add(new PlayerStoreEntry("Player", playerHues.get(i)));
            }
        }
        while (entries.size() > playerHues.size()) {
            entries.remove(entries.size() - 1);
        }
    }

    /**
     * Saves player data to file.
     */
    public void save() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYERS_FILE_NAME, false))){
            for (PlayerStoreEntry entry : entries){
                writer.write(entry.stringify());
                writer.newLine();
            }
            System.out.println("Players: Saved to file.");
        } catch (Exception e) {
            System.out.println("Error while saving players: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of player names.
     *
     * @return a list of player names
     */
    public ArrayList<String> getPlayers() {
        ArrayList<String> players = new ArrayList<>();
        for (PlayerStoreEntry entry : entries) {
            players.add(entry.getPlayerName());
        }
        return players;
    }

    /**
     * Returns a list of player hues.
     *
     * @return a list of player hues
     */
    public ArrayList<Double> getPlayerHues() {
        ArrayList<Double> playerHues = new ArrayList<>();
        for (PlayerStoreEntry entry : entries) {
            playerHues.add(entry.getPlayerHue());
        }
        return playerHues;
    }

    /**
     * Returns the singleton instance of PlayerStore.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of PlayerStore
     */
    public static PlayerStore getInstance() {
        if (instance == null)
            instance = new PlayerStore();
        return instance;
    }
}