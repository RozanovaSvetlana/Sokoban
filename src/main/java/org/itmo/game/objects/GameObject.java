package org.itmo.game.objects;

import com.googlecode.lanterna.TerminalRectangle;
import lombok.Getter;
import lombok.Setter;

public abstract class GameObject {
   
    @Setter
    @Getter
    protected TerminalRectangle position;
    
    protected GameObject(TerminalRectangle position) {
        this.position = position;
    }
}
