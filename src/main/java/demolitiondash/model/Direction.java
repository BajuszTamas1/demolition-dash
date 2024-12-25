package demolitiondash.model;

import demolitiondash.util.Coordinate;

import java.util.Random;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int x;
    public final int y;

    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Converts the direction to a velocity vector with the specified speed.
     *
     * @param speed The speed of the velocity vector.
     * @return The velocity vector corresponding to the direction.
     */
    public Coordinate toVelocityVector(int speed){
        return new Coordinate(x * speed, y * speed);
    }

    /**
     * Gets a random direction.
     *
     * @return A randomly selected direction.
     */
    public static Direction getRandom() {
        Direction[] directions = Direction.values();

        Random random = new Random();
        int randomIndex = random.nextInt(directions.length);

        return directions[randomIndex];
    }
}
