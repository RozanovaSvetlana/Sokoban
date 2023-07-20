package org.itmo.ui.view;

import static org.itmo.ui.Symbols.WALL;

import com.googlecode.lanterna.TextCharacter;
import org.itmo.game.objects.GameObject;
import org.itmo.ui.PrintAndManage;

public class WallView extends GameObjectView {
    public WallView(GameObject gameObject, int rowShift, int columnShift) {
        super(gameObject, rowShift, columnShift);
    }
    
    @Override
    public TextCharacter[] getView() {
        return new TextCharacter[] {WALL};
    }
    
    @Override
    public void print(PrintAndManage printAndManage) {
        printAndManage.wipeOut(getTerminalRectangle());
        printAndManage.drawRectangle(getTerminalRectangle(), getView()[0]);
    }
}
