package demolitiondash.model;

import demolitiondash.util.Coordinate;
import demolitiondash.util.Size;

public class Boundary {
    protected Coordinate location;
    protected Size size;

    public Boundary(Coordinate location, Size size){
        this.location = location;
        this.size = size;
    }

    public Coordinate getLocation(){
        return location;
    }
    public void setLocation(Coordinate location){
        this.location = location;
    }

    /**
     * Gets the center coordinate of the boundary.
     *
     * @return The center coordinate of the boundary.
     */
    public Coordinate getCenter(){
        return new Coordinate(
                this.location.x + this.size.width/2,
                this.location.y + this.size.height/2
        );
    }

    public Size getSize(){
        return size;
    }
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * Checks if this boundary is colliding with another boundary.
     *
     * @param other The other boundary to check collision with.
     * @return True if colliding, otherwise false.
     */
    public boolean isCollidingWith(Boundary other) {
        return this.location.x < other.location.x + other.size.width &&
                this.location.x + this.size.width > other.location.x &&
                this.location.y < other.location.y + other.size.height &&
                this.location.y + this.size.height > other.location.y;
    }

    /**
     * Checks if this boundary fully includes another boundary.
     *
     * @param other The other boundary to check inclusion with.
     * @return True if fully including, otherwise false.
     */
    public boolean includes(Boundary other) {
        return this.location.x <= other.location.x &&
                this.location.y <= other.location.y &&
                this.location.x + this.size.width >= other.location.x + other.size.width &&
                this.location.y + this.size.height >= other.location.y + other.size.height;
    }
}

