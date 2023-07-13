package org.itmo.game.objects;

import static org.itmo.game.Symbols.WALL;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import org.itmo.ui.PrintAndManage;

public class Wall extends GameObject implements GameObjectRepresentation {
    
    public Wall(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return new TextCharacter[] {WALL};
    }
    
    @Override
    public void print(PrintAndManage printAndManage, int rowShift, int columnShift) {
        TerminalRectangle positionWithShift = getPositionWithShift(rowShift, columnShift);
        printAndManage.wipeOut(positionWithShift);
        printAndManage.drawRectangle(positionWithShift, getRepresentation()[0]);
    }
}
