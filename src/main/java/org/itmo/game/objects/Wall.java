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
    public void print(PrintAndManage printAndManage) {
        printAndManage.wipeOut(position);
        printAndManage.drawRectangle(position, getRepresentation()[0]);
    }
}
