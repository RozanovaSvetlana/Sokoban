package org.itmo.game.objects;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalRectangle;
import java.util.HashMap;
import lombok.Getter;

public class WallsLoader {
    @Getter
    private final HashMap<TerminalPosition, TerminalPosition> walls = new HashMap<>();
    
    /**
     * Add new wall to the list or increase already added wall
     */
    public void getWalls(TerminalRectangle position, int i, int height) {
        if (!addVerticalWall(position.position, i) && !addHorizontalWall(position.position, height)) {
            walls.put(position.position, position.position);
        }
    }
    
    /**
     * Analyses the transmitted column for the presence of vertical walls and adds them to the list
     *
     * @return is exists vertical wall
     */
    private boolean addVerticalWall(TerminalPosition position, int column) {
        // if vertical wall already exists
        position = position.withRelativeRow(-2);
        if (walls.containsKey(position) && walls.get(position).getColumn() == column) {
            TerminalPosition start = walls.remove(position);
            walls.put(position.withRelativeRow(2), start);
            return true;
        }
        return false;
    }
    
    /**
     * Analyses the transmitted string for the presence of vertical walls and adds them to the list
     *
     * @return is exists horizontal wall
     */
    private boolean addHorizontalWall(TerminalPosition position, int height) {
        // if horizontal wall already exists
        position = position.withRelativeColumn(-2);
        if (walls.containsKey(position) && walls.get(position).getRow() == height) {
            TerminalPosition start = walls.remove(position);
            walls.put(position.withRelativeColumn(2), start);
            return true;
        }
        return false;
    }
}
