package org.itmo.game.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    
    private int x = 0;
    private int y = 0;
    
    public Position(Position position) {
        x = position.getX();
        y = position.getY();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return this.x == ((Position) obj).getX() && this.y == ((Position) obj).getY();
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.y;
        hash = 23 * hash + this.x;
        return hash;
    }
}
