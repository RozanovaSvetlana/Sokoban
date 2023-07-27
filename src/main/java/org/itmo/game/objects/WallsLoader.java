package org.itmo.game.objects;

import java.util.HashMap;
import lombok.Getter;

public class WallsLoader {
    @Getter
    private final HashMap<Position, Position> walls = new HashMap<>();
    
    /**
     * Add new wall to the list or increase already added wall
     */
    public void getWalls(Position position, int i, int height) {
        if (!addVerticalWall(position, i) && !addHorizontalWall(position, height)) {
            walls.put(position, position);
        }
    }
    
    /**
     * Analyses the transmitted column for the presence of vertical walls and adds them to the list
     *
     * @return is exists vertical wall
     */
    private boolean addVerticalWall(Position position, int column) {
        // if vertical wall already exists
        Position newPosition = new Position(position.getX(), position.getY() - 1);
        if (walls.containsKey(newPosition) && walls.get(newPosition).getX() == column) {
            Position start = walls.remove(newPosition);
            walls.put(position, start);
            return true;
        }
        return false;
    }
    
    /**
     * Analyses the transmitted string for the presence of vertical walls and adds them to the list
     *
     * @return is exists horizontal wall
     */
    private boolean addHorizontalWall(Position position, int height) {
        // if horizontal wall already exists
        Position newPosition = new Position(position.getX() - 1, position.getY());
        if (walls.containsKey(newPosition) && walls.get(newPosition).getY() == height) {
            Position start = walls.remove(newPosition);
            walls.put(position, start);
            return true;
        }
        return false;
    }
}
