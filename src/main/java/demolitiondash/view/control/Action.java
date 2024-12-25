package demolitiondash.view.control;

import demolitiondash.model.Direction;

public enum Action {
    MoveUp(Direction.UP),
    MoveDown(Direction.DOWN),
    MoveLeft(Direction.LEFT),
    MoveRight(Direction.RIGHT),
    PlaceBomb,
    PlaceBarrier;

    public final boolean isMovement;
    public final Direction direction;
    private Action(Direction d){
        isMovement = true;
        direction = d;
    }
    private Action(){
        isMovement = false;
        direction = null;
    }
}
