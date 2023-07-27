package org.itmo.game.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for converting GameObject to json and vice versa
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rectangle {
    
    private Position position;
    private int width = 1;
    private int height = 1;
    
    public Rectangle(Rectangle rectangle) {
        this.position = rectangle.getPosition();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }
    
    public Rectangle(int x, int y, int width, int height) {
        position = new Position(x, y);
        this.width = width;
        this.height = height;
    }
    
    public int XAndWidth() {
        return position.getX() + width;
    }
    
    public int YAndHeight() {
        return position.getY() + height;
    }
    
}
