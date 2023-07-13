package org.itmo.game.objects;

import static org.itmo.game.Symbols.ENDPOINT;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Endpoint extends GameObject implements GameObjectRepresentation {
    public Endpoint(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter getRepresentation() {
        return ENDPOINT;
    }
}
