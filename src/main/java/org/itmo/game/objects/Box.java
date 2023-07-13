package org.itmo.game.objects;

import static org.itmo.game.Symbols.RED_BOX_LEFT_BOTTOM;
import static org.itmo.game.Symbols.RED_BOX_LEFT_TOP;
import static org.itmo.game.Symbols.RED_BOX_RIGHT_BOTTOM;
import static org.itmo.game.Symbols.RED_BOX_RIGHT_TOP;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Box extends GameObject implements GameObjectRepresentation {
    
    TextCharacter[] currentCharacter = new TextCharacter[] {RED_BOX_LEFT_TOP, RED_BOX_RIGHT_TOP,
                                                            RED_BOX_LEFT_BOTTOM, RED_BOX_RIGHT_BOTTOM};
    
    public Box(TerminalRectangle position) {
        super(position);
    }
    
    /**
     * Makes the box green
     * Used when the crate is placed at the end point
     */
//    public void setEndpointBox() {
//        currentCharacter = GREEN_BOX;
//    }
    
    /**
     * Turns the crate red
     * Used if the crate is removed from the end point
     */
    public void setNotEndpointBox() {
        currentCharacter = new TextCharacter[] {RED_BOX_LEFT_TOP, RED_BOX_RIGHT_TOP,
                                                RED_BOX_LEFT_BOTTOM, RED_BOX_RIGHT_BOTTOM};
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return currentCharacter;
    }
}
