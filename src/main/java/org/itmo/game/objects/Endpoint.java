package org.itmo.game.objects;

import static org.itmo.game.Symbols.ENDPOINT;

import com.googlecode.lanterna.TerminalRectangle;

public class Endpoint extends GameObject{
    protected Endpoint(TerminalRectangle position) {
        super(ENDPOINT, position);
    }
}
