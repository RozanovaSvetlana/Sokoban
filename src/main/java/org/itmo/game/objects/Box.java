package org.itmo.game.objects;

import static org.itmo.game.Symbols.GREEN_BOX;
import static org.itmo.game.Symbols.RED_BOX;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Box extends GameObject implements GameObjectRepresentation {
    
    TextCharacter currentCharacter = RED_BOX;
    
    protected Box(TerminalRectangle position) {
        super(position);
    }
    
    /**
     * Makes the box green
     * Used when the crate is placed at the end point
     */
    public void setEndpointBox() {
        currentCharacter = GREEN_BOX;
    }
    
    /**
     * Turns the crate red
     * Used if the crate is removed from the end point
     */
    public void setNotEndpointBox() {
        currentCharacter = RED_BOX;
    }
    
    @Override
    public TextCharacter getRepresentation() {
        return currentCharacter;
    }
}
