package org.itmo.game.objects;

import com.googlecode.lanterna.TerminalRectangle;
import lombok.Getter;
import lombok.Setter;
import org.itmo.ui.PrintAndManage;

public abstract class GameObject implements GameObjectRepresentation{
   
    @Setter
    @Getter
    protected TerminalRectangle position;
    
    protected GameObject(TerminalRectangle position) {
        this.position = position;
    }
    
    @Override
    public void print(PrintAndManage printAndManage) {
        printAndManage.wipeOut(position);
        printAndManage.drawSpecialObject(position, getRepresentation());
    }
}
