package org.itmo.game.objects;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.graphics.TextGraphics;
import lombok.Getter;
import lombok.Setter;
import org.itmo.game.Symbols;
import org.itmo.ui.PrintAndManage;

public abstract class GameObject implements GameObjectRepresentation{
   
    @Setter
    @Getter
    protected TerminalRectangle position;
    
    protected GameObject(TerminalRectangle position) {
        this.position = position;
    }
    
    @Override
    public void print(PrintAndManage printAndManage, int rowShift, int columnShift) {
        TerminalRectangle positionWithShift = getPositionWithShift(rowShift, columnShift);
        printAndManage.wipeOut(positionWithShift);
        printAndManage.drawSpecialObject(positionWithShift, getRepresentation());
    }
    
    protected TerminalRectangle getPositionWithShift(int rowShift, int columnShift) {
        return new TerminalRectangle(position.x + columnShift,
            position.y + rowShift, position.width, position.height);
    }
}
