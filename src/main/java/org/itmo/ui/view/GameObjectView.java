package org.itmo.ui.view;

import com.googlecode.lanterna.TerminalRectangle;
import org.itmo.game.objects.GameObject;
import org.itmo.game.objects.Position;
import org.itmo.ui.PrintAndManage;

public abstract class GameObjectView implements GameObjectViewInterface {
    private Position position;
    private int width;
    private int height;
    private static int rowShift;
    private static int columnShift;
    
    public GameObjectView (GameObject gameObject, int rowShift, int columnShift) {
        position = gameObject.getRectangle().getPosition();
        width = gameObject.getRectangle().getWidth() * 2;
        height = gameObject.getRectangle().getHeight() * 2;
        GameObjectView.rowShift = rowShift;
        GameObjectView.columnShift = columnShift;
    }
    
    @Override
    public TerminalRectangle getTerminalRectangle() {
        return new TerminalRectangle(position.getX() * 2 + columnShift,
            position.getY() * 2 + rowShift, width, height);
    }
    
    @Override
    public void print(PrintAndManage printAndManage) {
        printAndManage.wipeOut(getTerminalRectangle());
        printAndManage.drawSpecialObject(getTerminalRectangle(), getView());
    }
    
}
