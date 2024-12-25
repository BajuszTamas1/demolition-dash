package demolitiondash.view.control.inputmap;

import demolitiondash.view.control.Action;
import demolitiondash.view.windows.GlobalSettingsView;

import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputStore {

    private static final String FILE_NAME = "controls.txt";
    private static InputStore instance;
    private final ArrayList<InputStoreEntry> controls;
    private InputStore() {
        controls = new ArrayList<>();
        if (!loadFromFile())
            resetToDefault();
    }

    private boolean loadFromFile() {
        controls.clear();
        try {
            String directoryPath = "/";
            Path path = Path.of(directoryPath + FILE_NAME);
//            if (!Files.exists(path)) {
//                Files.createFile(path);
//            }
            BufferedInputStream is = new BufferedInputStream(Files.newInputStream(Path.of(FILE_NAME)));
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                InputStoreEntry e = InputStoreEntry.fromLine(line);
                if (e != null) {
                    controls.add(e);
                }
            }
            System.out.println("Controls: Loaded from file.");
            return true;
        } catch (NoSuchFileException e) {
            System.out.println("Controls: File does not exist, reverting to default.");

            return false;
        } catch (Exception e) {
            System.out.println("Error while loading controls: " + e.getMessage());
            e.printStackTrace();
        }return false;
    }

    public void resetToDefault() {
        System.out.println("Controls: Setting default");
        controls.clear();
        controls.add(new InputStoreEntry(1, KeyEvent.VK_W, "W", Action.MoveUp));
        controls.add(new InputStoreEntry(1, KeyEvent.VK_S, "S", Action.MoveDown));
        controls.add(new InputStoreEntry(1, KeyEvent.VK_A, "A", Action.MoveLeft));
        controls.add(new InputStoreEntry(1, KeyEvent.VK_D, "D", Action.MoveRight));
        controls.add(new InputStoreEntry(1, KeyEvent.VK_E, "E", Action.PlaceBomb));
        controls.add(new InputStoreEntry(1, KeyEvent.VK_Q, "Q", Action.PlaceBarrier));

        controls.add(new InputStoreEntry(2, KeyEvent.VK_I, "I", Action.MoveUp));
        controls.add(new InputStoreEntry(2, KeyEvent.VK_K, "K", Action.MoveDown));
        controls.add(new InputStoreEntry(2, KeyEvent.VK_J, "J", Action.MoveLeft));
        controls.add(new InputStoreEntry(2, KeyEvent.VK_L, "L", Action.MoveRight));
        controls.add(new InputStoreEntry(2, KeyEvent.VK_O, "O", Action.PlaceBomb));
        controls.add(new InputStoreEntry(2, KeyEvent.VK_U, "U", Action.PlaceBarrier));

        controls.add(new InputStoreEntry(3, KeyEvent.VK_UP, "⇧", Action.MoveUp));
        controls.add(new InputStoreEntry(3, KeyEvent.VK_DOWN, "⇩", Action.MoveDown));
        controls.add(new InputStoreEntry(3, KeyEvent.VK_LEFT, "⇦", Action.MoveLeft));
        controls.add(new InputStoreEntry(3, KeyEvent.VK_RIGHT, "⇨", Action.MoveRight));

        controls.add(new InputStoreEntry(3, KeyEvent.VK_SHIFT, "R_S", Action.PlaceBomb));
        controls.add(new InputStoreEntry(3, KeyEvent.VK_CONTROL, "R_C", Action.PlaceBarrier));
    }

    /**
     * Used for setting a certain control for a certain player.
     * @param playerNo Number of the player (1,2,3).
     * @param keyCode Keycode of the key.
     * @param keyLabel Label of the key used for representation in the UI.
     * @param action Action of the key.
     * @return True if the given key is unique, false if not and previous entries were invalidated.
     */
    public boolean setPlayerControl(int playerNo, int keyCode, String keyLabel, Action action) {
        boolean invalidElements = invalidateCollidingControls(keyCode);
        InputStoreEntry entry =
                controls.stream()
                        .filter((e -> e.getPlayerNumber() == playerNo && e.getAction() == action))
                        .findAny()
                        .orElse(null);
        if (entry == null)
            controls.add(new InputStoreEntry(playerNo, keyCode, keyLabel, action));
        else {
            entry.setKeyCode(keyCode);
            entry.setKeyLabel(keyLabel);
        }
        return !invalidElements;
    }


    private boolean invalidateCollidingControls(int keyCode) {
        List<InputStoreEntry> entries = controls.stream().filter((e -> e.getKeyCode() == keyCode)).toList();
        for (InputStoreEntry e : entries) {
            e.invalidate();
        }
        return !entries.isEmpty();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (InputStoreEntry e : controls) {
                writer.write(e.stringify());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error while saving controls: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean hasInvalidateControls() {
        return controls.stream().anyMatch(InputStoreEntry::isInvalid);
    }

    public ArrayList<InputStoreEntry> getControls() {
        return new ArrayList<InputStoreEntry>(controls);
    }

    public static InputStore getInstance() {
        if (instance == null)
            instance = new InputStore();
        return instance;
    }


}
