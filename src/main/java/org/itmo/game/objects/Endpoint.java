package org.itmo.game.objects;

import static org.itmo.game.Symbols.ENDPOINT_LEFT;
import static org.itmo.game.Symbols.ENDPOINT_RIGHT;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import org.itmo.ui.PrintAndManage;

public class Endpoint extends GameObject implements GameObjectRepresentation {
    public Endpoint(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return new TextCharacter[] {ENDPOINT_RIGHT, ENDPOINT_LEFT, ENDPOINT_LEFT, ENDPOINT_RIGHT};
    }
    
    @Override
    public void print(PrintAndManage printAndManage, int rowShift, int columnShift) {
        TerminalRectangle positionWithShift = getPositionWithShift(rowShift, columnShift);
        printAndManage.wipeOut(positionWithShift);
        printAndManage.drawSpecialObject(positionWithShift, getRepresentation());
    }
}
