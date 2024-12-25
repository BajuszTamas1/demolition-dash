package demolitiondash.view.control;

import demolitiondash.model.Direction;
import demolitiondash.model.map.Map;
import demolitiondash.view.control.inputmap.InputStore;
import demolitiondash.view.control.inputmap.InputStoreEntry;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Control extends KeyAdapter {
    //to be replaced by data coming from InputStore
    private static final int MAX_PLAYER_COUNT = 3;

    private HashMap<Integer, ControlWrapper> controls = new HashMap<>();
    private ArrayList<EnumSet<Direction>> activeDirections = new ArrayList<>();
    private Map map;

    public Control(Map map){
        this.map = map;
        for(int i = 0; i < MAX_PLAYER_COUNT; i++){
            activeDirections.add(EnumSet.noneOf(Direction.class));
        }
        InputStore store = InputStore.getInstance();
        List<InputStoreEntry> entries = store.getControls();
        for(InputStoreEntry e : entries){
            controls.put(e.getKeyCode(), new ControlWrapper(e.getPlayerNumber(), e.getAction()));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if(!controls.containsKey(keycode))
            return;

        ControlWrapper wrapper = controls.get(keycode);
        int playerNo = wrapper.getPlayerNo();
        Action action = wrapper.getAction();
        if(action.isMovement){
            activeDirections.get(playerNo - 1).add(action.direction);
            map.handlePlayerMovement(playerNo, new ArrayList<>(activeDirections.get(playerNo - 1)));
        }else{
            if(action == Action.PlaceBomb)
                map.handlePlayerBombPlacement(playerNo);
            else if(action == Action.PlaceBarrier)
                map.handleBaricadeOutline(playerNo);
        }

    }

    @Override
    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        if(!controls.containsKey(keycode))
            return;

        ControlWrapper wrapper = controls.get(keycode);
        int playerNo = wrapper.getPlayerNo();
        Action action = wrapper.getAction();
        if(action.isMovement){
            activeDirections.get(playerNo - 1).remove(action.direction);
            map.handlePlayerMovement(playerNo, new ArrayList<>(activeDirections.get(playerNo - 1)));
        }
        else if(action == Action.PlaceBarrier)
            map.handleBarricadePlacement(playerNo);
    }

}
