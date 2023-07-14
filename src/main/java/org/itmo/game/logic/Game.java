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
                changePosition(map.getPlayer(),
                    getNewPositionBasedOnDirection(map.getPlayer().getPosition(), UP));
            }
            case DOWN -> {
                map.getPlayer().setStraight();
                changePosition(map.getPlayer(),
                    getNewPositionBasedOnDirection(map.getPlayer().getPosition(), DOWN));
            }
            case LEFT -> {
                map.getPlayer().setLeftTurn();
                changePosition(map.getPlayer(),
                    getNewPositionBasedOnDirection(map.getPlayer().getPosition(), LEFT));
            }
            case RIGHT -> {
                map.getPlayer().setRightTurn();
                changePosition(map.getPlayer(),
                    getNewPositionBasedOnDirection(map.getPlayer().getPosition(), RIGHT));
            }
        }
        window.reprintPlayer(oldPosition);
        window.printEndpointOnPosition(oldPosition);
        if(!oldPosition.equals(map.getPlayer().getPosition())) {
            numberStep++;
            window.updateNumberSteps(numberStep);
        }
        window.refreshScreen();
        return false;
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
    
    private void moveBox() {
        //изменяем местоположение ящика
        //делаем проверку, если ящик встал/ушел с конечной точки, то изменяем его
    }
    
    public boolean isSolved() {
        //проверяет, решен ли уровень
        return false;
    }
    
}
