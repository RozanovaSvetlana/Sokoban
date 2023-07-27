package org.itmo.game.objects;

import static org.itmo.game.objects.Type.PLAYER;

public class Player extends GameObject {
    
    public Player(Rectangle rectangle) {
        super(rectangle);
    }
    
    @Override
    public Type getType() {
        return PLAYER;
    }
}
