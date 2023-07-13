package org.itmo.game.logic;

import static java.lang.Math.max;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import org.itmo.game.map.Map;
import org.itmo.ui.windows.GameWindow;
import org.itmo.ui.windows.LogoWindow;
import org.itmo.ui.windows.WindowImpl;

public class Game {
    
    Map map;
    int numberOccupiedEndpoints = 0;
    int numberStep = 0;
    WindowImpl currentWindow;
    
    public void toLogoWindow() throws IOException {
        currentWindow = new LogoWindow();
        currentWindow.clearScreen();
        currentWindow.play();
    }
    
    public void toGameWindow(String fileName) throws IOException {
        map = Map.builder().setFileName(fileName).build();
        currentWindow = new GameWindow(max(map.getWidth(), GameWindow.minColumnSize),
            map.getHeight() + GameWindow.minRowSize);
        currentWindow.clearScreen();
        currentWindow.play();
    }
    
    public KeyStroke getKeyPressed() throws IOException {
        return currentWindow.getKeyPressed();
    }
    
    public void closeWindow() throws IOException {
        currentWindow.closeTerminal();
    }
    
    public boolean takeStep(Direction direction) {
        //делаем шаг, если возможно
        return false;
    }
    
    private boolean isEmptyCell() {
        //проверяем, пустая ли ячейка, чтобы понять, можно ли сделать шаг/подвинуть ящик
        return false;
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
