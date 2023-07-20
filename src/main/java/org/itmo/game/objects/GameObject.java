package org.itmo.game.objects;

import lombok.Setter;

public abstract class GameObject implements GameObjectRepresentation{
    
    @Setter
    Rectangle rectangle;
    
    protected GameObject(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
