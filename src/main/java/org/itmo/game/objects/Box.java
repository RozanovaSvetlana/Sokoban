package org.itmo.game.objects;

import static org.itmo.game.objects.Type.BOX;

public class Box extends GameObject {
    
    public Box(Box box) {
        super(new Rectangle(new Position(box.getRectangle().getPosition()),
            box.getRectangle().getWidth(), box.getRectangle().getHeight()));
    }
    
    public Box(Rectangle rectangle) {
        super(rectangle);
    }
    
    @Override
    public Type getType() {
        return BOX;
    }
}
