package org.itmo.game.objects;

import static org.itmo.game.objects.Type.WALL;

public class Wall extends GameObject {
    
    public Wall(Rectangle rectangle) {
        super(rectangle);
    }
    
    @Override
    public Type getType() {
        return WALL;
    }
}
