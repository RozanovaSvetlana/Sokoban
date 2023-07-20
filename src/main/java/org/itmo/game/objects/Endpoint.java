package org.itmo.game.objects;

import static org.itmo.game.objects.Type.ENDPOINT;

public class Endpoint extends GameObject {
    
    public Endpoint(Rectangle rectangle) {
        super(rectangle);
    }
    
    @Override
    public Type getType() {
        return ENDPOINT;
    }
}
