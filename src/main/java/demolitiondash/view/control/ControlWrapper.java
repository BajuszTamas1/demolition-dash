package demolitiondash.view.control;

public class ControlWrapper {
    //dummy solution
    private final int playerNo;
    private final Action action;
    public ControlWrapper(int playerNo, Action action){
        this.playerNo = playerNo;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
    public int getPlayerNo() {
        return playerNo;
    }
}
