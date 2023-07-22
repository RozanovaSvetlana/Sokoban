package org.itmo.game.logic;

import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Getter;
import org.itmo.game.map.Map;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.GameObject;
import org.itmo.game.objects.Position;
import org.itmo.utils.FileUtils;

public class Game {
    
    Map map;
    int numberOccupiedEndpoints = 0;
    
    @Getter
    int numberStep = 0;
    
    /**
     * Set map from file
     * @param fileName - map file
     * @return Map
     */
    public Map setMap(String fileName) {
        map = Map.builder().setFileName(fileName).build();
        setNumberOccupiedEndpoints();
        return map;
    }
    
    /**
     * Set map
     * @param map - Map
     */
    public void setMap(Map map) {
        this.map = map;
        setNumberOccupiedEndpoints();
    }
    
    /**
     * Set number occupied endpoints
     */
    private void setNumberOccupiedEndpoints() {
        numberOccupiedEndpoints = (int) map.getBoxes().stream().filter((x) ->
            isEndpoint(x.getRectangle()
            .getPosition())).count();
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
        return isWin();
    }
    
    /**
     * Makes a move for the player
     *
     * @param direction - step direction
     */
    public boolean step(Direction direction) {
        Position newPositionBasedOnDirection =
            getNewPositionBasedOnDirection(map.getPlayer().getRectangle().getPosition(), direction);
        if(isBox(newPositionBasedOnDirection)) {
            if(moveBox(newPositionBasedOnDirection, direction)) {
                changePosition(map.getPlayer(), newPositionBasedOnDirection);
                return true;
            }
        } else {
            return changePosition(map.getPlayer(), newPositionBasedOnDirection);
        }
        return false;
    }
    
    /**
     * Calculates the following position according to the direction indicated
     *
     * @param oldPosition - position to be calculated for
     * @param direction - step direction
     * @return new position
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
    private boolean changePosition(GameObject gameObject, Position newPosition) {
        if(!isWall(newPosition)) {
            Position position = gameObject.getRectangle().getPosition();
            position.setX(newPosition.getX());
            position.setY(newPosition.getY());
            return true;
        }
        return false;
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
    
    /**
     * Checks if the level has been passed
     * @return true - if the level is passed, false - otherwise
     */
    public boolean isWin() {
        return numberOccupiedEndpoints == map.getEndpoints().size();
    }
    
    /**
     * Checks if a box exists in a corner and not on an endpoint
     *
     * @return true - if exists, false - otherwise
     */
    public boolean isExistsBoxNotOnEndpointButInCorner() {
        return map.getBoxes().stream().filter((x) -> !isEndpoint(x.getRectangle().getPosition()))
            .anyMatch((x) -> {
                boolean left = isWall(getNewPositionBasedOnDirection(x.getRectangle().getPosition(),
                    LEFT));
                boolean right =
                    isWall(getNewPositionBasedOnDirection(x.getRectangle().getPosition(),
                    RIGHT));
                boolean down =
                    isWall(getNewPositionBasedOnDirection(x.getRectangle().getPosition(),
                        DOWN));
                boolean up =
                    isWall(getNewPositionBasedOnDirection(x.getRectangle().getPosition(),
                        UP));
                return (left && up) || (left && down) || (up && right) || (right && down);
            });
    }
    
    public String writeMapToFile() {
        String fileNameForCurrentMap = "map_for_solution.json";
        try (OutputStream fileForWrite = FileUtils.getFileForWrite(fileNameForCurrentMap)) {
            fileForWrite.write(map.fromMapToJsonString().getBytes(StandardCharsets.UTF_8));
            return fileNameForCurrentMap;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
