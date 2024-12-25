package demolitiondash.view.control.inputmap;

import demolitiondash.view.control.Action;

public class InputStoreEntry {
    private final int playerNumber;
    private final Action action;
    private int keyCode;
    private String keyLabel;

    public InputStoreEntry(int playerNumber, int keyCode, String keyLabel, Action action) {
        this.playerNumber = playerNumber;
        this.action = action;
        this.keyCode = keyCode;
        this.keyLabel = keyLabel;
    }

    public static InputStoreEntry fromLine(String line) {
        if (line == null)
            return null;
        String[] data = line.split(",");
        if (data.length < 4)
            return null;

        try {
            int playerIndex = Integer.parseInt(data[0]);
            Action action = Action.valueOf(data[1]);
            int keyCode = Integer.parseInt(data[2]);
            String keyLabel = data[3];
            return new InputStoreEntry(playerIndex, keyCode, keyLabel, action);
        } catch (Exception e) {
            System.out.print("Invalid format for control: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Action getAction() {
        return action;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public String getKeyLabel() {
        return keyLabel;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public void setKeyLabel(String keyLabel) {
        this.keyLabel = keyLabel;
    }

    public void invalidate() {
        this.keyCode = -1;
        this.keyLabel = "(???)";
    }

    public boolean isInvalid() {
        return keyCode == -1;
    }

    @Override
    public String toString() {
        return "InputStoreEntry:" + stringify();
    }

    public String stringify() {
        return playerNumber + "," + action + "," + keyCode + "," + keyLabel;
    }
}
