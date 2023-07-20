package org.itmo.game.logic;

import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import java.io.IOException;
import java.util.List;
import lombok.Getter;
import org.itmo.game.map.Map;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.GameObject;
import org.itmo.game.objects.Position;

public class Game {
    
    Map map;
    int numberOccupiedEndpoints = 0;
    
    @Getter
    int numberStep = 0;
    
    public Map setMap(String fileName) {
        map = Map.builder().setFileName(fileName).build();
        return map;
    }
    
    /**
     * Takes a step in the specified direction and causes a redraw if necessary
     *
     * @param direction - step direction
     * @return True - if the step was the last (i.e. the game is over), false - otherwise
     * @throws IOException
     */
    public boolean takeStep(Direction direction) throws IOException {
        Position oldPosition = new Position(map.getPlayer().getRectangle().getPosition());
        switch (direction) {
            case UP -> {
                step(UP);
            }
            case DOWN -> {
                step(DOWN);
            }
            case LEFT -> {
                step(LEFT);
            }
            case RIGHT -> {
                step(RIGHT);
            }
        }
        if(!oldPosition.equals(map.getPlayer().getRectangle().getPosition())) {
            numberStep++;
        }
        if(numberOccupiedEndpoints == map.getEndpoints().size()) {
            return true;
        }
        return false;
    }
    
    /**
     * Makes a move for the player
     *
     * @param direction - step direction
     */
    private void step(Direction direction) {
        Position newPositionBasedOnDirection =
            getNewPositionBasedOnDirection(map.getPlayer().getRectangle().getPosition(), direction);
        if(isBox(newPositionBasedOnDirection)) {
            if(moveBox(newPositionBasedOnDirection, direction)) {
                changePosition(map.getPlayer(), newPositionBasedOnDirection);
            }
        } else {
            changePosition(map.getPlayer(), newPositionBasedOnDirection);
        }
    }
    
    /**
     * Calculates the following position according to the direction indicated
     *
     * @param oldPosition - position to be calculated for
     * @param direction - step direction
     * @return
     */
    private Position getNewPositionBasedOnDirection(Position oldPosition,
                                                     Direction direction) {
        switch (direction) {
            case DOWN -> {
                return new Position(oldPosition.getX(), oldPosition.getY() + 1);
            }
            case UP -> {
                return new Position(oldPosition.getX(), oldPosition.getY() - 1);
            }
            case LEFT -> {
                return new Position(oldPosition.getX() - 1, oldPosition.getY());
            }
            case RIGHT -> {
                return new Position(oldPosition.getX() + 1, oldPosition.getY());
            }
            default -> throw new RuntimeException();
        }
    }
    
    /**
     * Changes the position of the GameObject
     *
     * @param gameObject - object that needs to change position
     * @param newPosition - new object position
     */
    private void changePosition(GameObject gameObject, Position newPosition) {
        if(!isWall(newPosition)) {
            Position position = gameObject.getRectangle().getPosition();
            position.setX(newPosition.getX());
            position.setY(newPosition.getY());
        }
    }
    
    /**
     * Checks if any object from the list is at the specified position
     *
     * @param list - list of objects to be checked
     * @param position - test position
     * @return true - if at least one of the objects from the list is located (crosses)
     * the passed position, false - otherwise
     */
    private boolean isGameObjectInCell(List<? extends GameObject> list, Position position) {
        return list.stream().anyMatch((x) -> isBetween(x.getRectangle().getPosition().getX(),
            position.getX(),
            x.getRectangle().XAndWidth()) && isBetween(x.getRectangle().getPosition().getY(),
            position.getY(),
            x.getRectangle().YAndHeight()));
    }
    
    /**
     * Checks if the wall is in the specified position
     * @param position - test position
     * @return true - if there is a wall in the specified position (crosses it), false - otherwise
     */
    private boolean isWall(Position position) {
        return isGameObjectInCell(map.getWalls(), position);
    }
    
    /**
     * Checks if the box is in the specified position
     * @param position - test position
     * @return true - if there is a box in the specified position, false - otherwise
     */
    private boolean isBox(Position position) {
        return isGameObjectInCell(map.getBoxes(), position);
    }
    
    /**
     * Checks if the endpoint is in the specified position
     * @param position - test position
     * @return true - if there is a endpoint in the specified position, false -
     * otherwise
     */
    private boolean isEndpoint(Position position) {
        return isGameObjectInCell(map.getEndpoints(), position);
    }
    
    /**
     * Returns true if value between left and right coordinates
     */
    public boolean isBetween(int left, int value, int right) {
        return left <= value && right > value;
    }
    
    /**
     * Moves the box according to the direction
     *
     * @param position - drawer position
     *
     * @param direction - direction of movement
     * @return true - if the box was moved, false - otherwise
     */
    private boolean moveBox(Position position, Direction direction) {
        Position newRectangleBasedOnDirection =
            getNewPositionBasedOnDirection(position, direction);
        if(!isWall(newRectangleBasedOnDirection) && !isBox(newRectangleBasedOnDirection)) {
            Box box = map.getBoxes().stream().filter((x) -> x.getRectangle().getPosition()
                    .equals(position)).findFirst().get();
            if(isEndpoint(box.getRectangle().getPosition())) {
                numberOccupiedEndpoints--;
            }
            changePosition(box, newRectangleBasedOnDirection);
            if(isEndpoint(newRectangleBasedOnDirection)) {
                numberOccupiedEndpoints++;
            }
            return true;
        }
        return false;
    }
    
}
