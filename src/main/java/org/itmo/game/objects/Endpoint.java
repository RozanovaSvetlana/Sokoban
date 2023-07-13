package org.itmo.game.objects;

import static org.itmo.game.Symbols.ENDPOINT_LEFT_BOTTOM;
import static org.itmo.game.Symbols.ENDPOINT_LEFT_TOP;
import static org.itmo.game.Symbols.ENDPOINT_RIGHT_BOTTOM;
import static org.itmo.game.Symbols.ENDPOINT_RIGHT_TOP;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Endpoint extends GameObject implements GameObjectRepresentation {
    public Endpoint(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return new TextCharacter[] {ENDPOINT_RIGHT_TOP, ENDPOINT_LEFT_TOP,
                                    ENDPOINT_RIGHT_BOTTOM, ENDPOINT_LEFT_BOTTOM};
    }
    
}
