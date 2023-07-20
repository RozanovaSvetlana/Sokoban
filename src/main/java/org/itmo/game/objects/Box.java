package org.itmo.game.objects;

import static org.itmo.game.objects.Type.BOX;

public class Box extends GameObject {
    
    public Box(Rectangle rectangle) {
        super(rectangle);
    }
    
    @Override
    public Type getType() {
        return BOX;
    }
}
