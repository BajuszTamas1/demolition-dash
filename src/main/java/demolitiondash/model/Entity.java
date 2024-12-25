package demolitiondash.model;

import demolitiondash.util.Coordinate;
import demolitiondash.util.Disposable;
import demolitiondash.util.Size;

import java.util.List;

public abstract class Entity extends Boundary implements Disposable, Cloneable {

    public Entity(Coordinate location, Size size){
        super(location, size);
    }
    public Boundary getHitbox(){
        return this;
    }

    /**
     * Checks if the entity is colliding with another entity.
     *
     * @param entity The other entity to check collision with.
     * @return True if the entities are colliding, otherwise false.
     */
    public boolean isCollidingWith(Entity entity) {
        return getHitbox().isCollidingWith(entity.getHitbox());
    }

    /**
     * Checks if the entity is colliding with any entities in the given list.
     *
     * @param entities The list of entities to check collision with.
     * @return True if the entity is colliding with any entity in the list, otherwise false.
     */
    public boolean isCollidingWithAny(List<Entity> entities) {
        return entities.stream().anyMatch(this::isCollidingWith);
    }

    public boolean isReadyToGetDisposed(){
        return false;
    }

    @Override
    public Entity clone() {
        try {
            return (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
