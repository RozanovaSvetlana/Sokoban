package org.itmo.game.objects;

import static org.itmo.game.Symbols.PLAYER;

import com.googlecode.lanterna.TerminalRectangle;

public class Player extends GameObject{
    protected Player(TerminalRectangle position) {
        super(PLAYER, position);
    }
}
