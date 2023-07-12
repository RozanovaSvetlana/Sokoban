package org.itmo.game.objects;

import static org.itmo.constants.Symbols.GREEN_BOX;
import static org.itmo.constants.Symbols.RED_BOX;

import com.googlecode.lanterna.TerminalRectangle;

public class Box extends GameObject{
    protected Box(TerminalRectangle position) {
        super(RED_BOX, position);
    }
    
    /**
     * Makes the box green
     * Used when the crate is placed at the end point
     */
    public void setEndpointBox() {
        setCharacter(GREEN_BOX);
    }
    
    /**
     * Turns the crate red
     * Used if the crate is removed from the end point
     */
    public void setNotEndpointBox() {
        setCharacter(RED_BOX);
    }
}
