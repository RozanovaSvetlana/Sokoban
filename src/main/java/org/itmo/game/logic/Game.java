package org.itmo.game.logic;

import org.itmo.constants.Direction;
import org.itmo.game.map.Map;

public class Game {
    
    Map map;
    int numberOccupiedEndpoints = 0;
    
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
