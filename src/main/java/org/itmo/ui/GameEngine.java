package org.itmo.ui;

import static java.lang.Math.max;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import org.itmo.game.logic.Direction;
import org.itmo.game.map.Map;
import org.itmo.ui.windows.GameWindow;
import org.itmo.ui.windows.LogoWindow;
import org.itmo.ui.windows.WindowImpl;
import org.itmo.utils.FileUtils;

public class GameEngine {
    Map map;
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
    public void toGameWindow(Map map, String fileName) throws IOException {
        this.map = map;
        String name = FileUtils.getFileName(fileName);
        int maxColumn = max(GameWindow.winLength, GameWindow.minColumnSize + name.length());
        currentWindow = new GameWindow(max(map.getWidth() * 2 + 2, maxColumn),
            map.getHeight() * 2 + GameWindow.minRowSize + 2, name, map);
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
    
    /**
     * Prints the current map and updates the number of steps
     *
     * @param numberStep - the number of steps a character has taken
     * @param direction - direction of movement
     * @throws IOException
     */
    public void printMap(int numberStep, Direction direction) throws IOException {
        window.printMap(numberStep, direction);
        window.refreshScreen();
    }
    
    /**
     * Updates the map and the number of steps and sets the winning inscription
     *
     * @param numberStep - the number of steps a character has taken
     * @param direction - direction of movement
     * @throws IOException
     */
    public void setWin(int numberStep, Direction direction) throws IOException {
        window.printMap(numberStep, direction);
        window.setWin();
        window.refreshScreen();
    }
}
