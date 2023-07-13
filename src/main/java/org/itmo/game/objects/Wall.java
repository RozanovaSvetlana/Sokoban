package org.itmo.game.objects;

import static org.itmo.game.Symbols.WALL;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Wall extends GameObject implements GameObjectRepresentation {
    
    public Wall(TerminalRectangle position) {
        super(position);
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return new TextCharacter[] {WALL};
    }
}
