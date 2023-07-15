package org.itmo.game.logic;

import static java.lang.Math.max;
import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.itmo.game.map.Map;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.GameObject;
import org.itmo.ui.windows.GameWindow;
import org.itmo.ui.windows.LogoWindow;
import org.itmo.ui.windows.WindowImpl;
import org.itmo.utils.FileUtils;

public class Game {
    
    Map map;
    int numberOccupiedEndpoints = 0;
    int numberStep = 0;
    WindowImpl currentWindow;
    GameWindow window;
    
    /**
     * Launches the logo window
     * @throws IOException
     */
    public void toLogoWindow() throws IOException {
        currentWindow = new LogoWindow();
        currentWindow.clearScreen();
        currentWindow.play();
    }
    
    /**
     * Launches the game window
     *
     * @param fileName - map file name
     * @throws IOException
     */
    public void toGameWindow(String fileName) throws IOException {
        map = Map.builder().setFileName(fileName).build();
        String name = FileUtils.getFileName(fileName);
        currentWindow = new GameWindow(max(map.getWidth() + 2, GameWindow.minColumnSize + name.length()),
            map.getHeight() + GameWindow.minRowSize, name, map);
        updateMapWithShift(GameWindow.getRowShift(), GameWindow.getColumnShift());
        currentWindow.clearScreen();
        currentWindow.play();
        window = (GameWindow) currentWindow;
    }
    
    /**
     * Returns the pressed key
     *
     * @return KeyStroke
     * @throws IOException
     */
    public KeyStroke getKeyPressed() throws IOException {
        return currentWindow.getKeyPressed();
    }
    
    /**
     * Close terminal
     *
     * @throws IOException
     */
    public void closeWindow() throws IOException {
        currentWindow.closeTerminal();
    }
    
    public boolean takeStep(Direction direction) throws IOException {
        TerminalRectangle oldPosition = map.getPlayer().getPosition();
        switch (direction) {
            case UP -> {
                map.getPlayer().setStraight();
                step(UP);
            }
            case DOWN -> {
                map.getPlayer().setStraight();
                step(DOWN);
            }
            case LEFT -> {
                map.getPlayer().setLeftTurn();
                step(LEFT);
            }
            case RIGHT -> {
                map.getPlayer().setRightTurn();
                step(RIGHT);
            }
        }
        window.reprintPlayer(oldPosition);
        if(!oldPosition.equals(map.getPlayer().getPosition())) {
            window.printEndpointOnPosition(oldPosition);
            numberStep++;
            window.updateNumberSteps(numberStep);
        }
        window.refreshScreen();
        return false;
    }
    
    private void step(Direction direction) {
        TerminalRectangle newPositionBasedOnDirection =
            getNewPositionBasedOnDirection(map.getPlayer().getPosition(), direction);
        if(isBox(newPositionBasedOnDirection)) {
            if(moveBox(newPositionBasedOnDirection, direction)) {
                changePosition(map.getPlayer(), newPositionBasedOnDirection);
            }
        } else {
            changePosition(map.getPlayer(), newPositionBasedOnDirection);
        }
    }
    
    private TerminalRectangle getNewPositionBasedOnDirection(TerminalRectangle oldPosition,
                                                             Direction direction) {
        switch (direction) {
            case DOWN -> {
                return new TerminalRectangle(oldPosition.x, oldPosition.y + 2, oldPosition.width,
                    oldPosition.height);
            }
            case UP -> {
                return new TerminalRectangle(oldPosition.x, oldPosition.y - 2, oldPosition.width,
                    oldPosition.height);
            }
            case LEFT -> {
                return new TerminalRectangle(oldPosition.x - 2, oldPosition.y, oldPosition.width,
                    oldPosition.height);
            }
            case RIGHT -> {
                return new TerminalRectangle(oldPosition.x + 2, oldPosition.y, oldPosition.width,
                    oldPosition.height);
            }
            default -> throw new RuntimeException();
        }
    }
    
    private void changePosition(GameObject gameObject, TerminalRectangle newPosition) {
        if(!isWall(newPosition)) {
            gameObject.setPosition(newPosition);
        }
    }
    
    private boolean isGameObjectInCell(List<? extends GameObject> list, TerminalRectangle position) {
        return list.stream().anyMatch((x) -> isBetween(x.getPosition().x, position.x,
            x.getPosition().xAndWidth) && isBetween(x.getPosition().y, position.y,
            x.getPosition().yAndHeight));
    }
    
    private boolean isWall(TerminalRectangle position) {
        return isGameObjectInCell(map.getWalls(), position);
    }
    
    private boolean isBox(TerminalRectangle position) {
        return isGameObjectInCell(map.getBoxes(), position);
    }
    
    private boolean isEndpoint(TerminalRectangle position) {
        return isGameObjectInCell(map.getEndpoints(), position);
    }
    
    /**
     * Returns true if value between left and right coordinates
     */
    public boolean isBetween(int left, int value, int right) {
        return left <= value && right > value;
    }
    
    private void updateMapWithShift(int rowShift, int columnShift) {
        updateGameObjectPositionWithShift(map.getWalls(), rowShift, columnShift);
        updateGameObjectPositionWithShift(map.getBoxes(), rowShift, columnShift);
        updateGameObjectPositionWithShift(map.getEndpoints(), rowShift, columnShift);
        updateGameObjectPositionWithShift(Collections.singletonList(map.getPlayer()), rowShift,
            columnShift);
    }
    
    private void updateGameObjectPositionWithShift(List<? extends GameObject> list, int rowShift,
                                                   int columnShift) {
        list.stream().forEach((x) -> {
            TerminalRectangle position = x.getPosition();
            x.setPosition(
                new TerminalRectangle(position.x + columnShift, position.y + rowShift,
                    position.width, position.height));
        });
    }
    
    private boolean moveBox(TerminalRectangle position, Direction direction) {
        TerminalRectangle newPositionBasedOnDirection =
            getNewPositionBasedOnDirection(position, direction);
        if(!isWall(newPositionBasedOnDirection) && !isBox(newPositionBasedOnDirection)) {
            Box box = map.getBoxes().stream().filter((x) -> x.getPosition().equals(position))
                .findFirst().get();
            if(isEndpoint(box.getPosition())) {
                box.setNotEndpointBox();
                numberOccupiedEndpoints--;
            }
            changePosition(box, newPositionBasedOnDirection);
            if(isEndpoint(newPositionBasedOnDirection)) {
                box.setEndpointBox();
                numberOccupiedEndpoints++;
            }
            window.printBox(box);
            return true;
        }
        return false;
    }
    
    public boolean isSolved() {
        //проверяет, решен ли уровень
        return false;
    }
    
}
