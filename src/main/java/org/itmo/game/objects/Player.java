package org.itmo.game.objects;

import static org.itmo.game.Symbols.PLAYER_LEFT_SIDE_OF_BODY;
import static org.itmo.game.Symbols.PLAYER_LEFT_SIDE_OF_HEAD;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_BODY;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_HEAD;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import org.itmo.ui.PrintAndManage;

public class Player extends GameObject implements GameObjectRepresentation{
    public Player(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return new TextCharacter[] {PLAYER_LEFT_SIDE_OF_HEAD, PLAYER_RIGHT_SIDE_OF_HEAD,
                                    PLAYER_LEFT_SIDE_OF_BODY, PLAYER_RIGHT_SIDE_OF_BODY};
    }
    
    @Override
    public void print(PrintAndManage printAndManage, int rowShift, int columnShift) {
        TerminalRectangle positionWithShift = getPositionWithShift(rowShift, columnShift);
        printAndManage.wipeOut(positionWithShift);
        printAndManage.drawSpecialObject(positionWithShift, getRepresentation());
    }
}
