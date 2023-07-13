package org.itmo.game.objects;

import static org.itmo.game.Symbols.WALL;

import com.googlecode.lanterna.TerminalRectangle;

public class Wall extends GameObject {
    public Wall(TerminalRectangle position) {
        super(WALL, position);
    }
}
