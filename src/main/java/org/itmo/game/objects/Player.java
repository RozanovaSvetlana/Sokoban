package org.itmo.game.objects;

import static org.itmo.game.Symbols.PLAYER;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Player extends GameObject implements GameObjectRepresentation{
    public Player(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter getRepresentation() {
        return PLAYER;
    }
}
