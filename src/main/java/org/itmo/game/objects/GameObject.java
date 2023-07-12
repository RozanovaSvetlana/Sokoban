package org.itmo.game.objects;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import lombok.Getter;
import lombok.Setter;

public abstract class GameObject {
    
    @Setter
    private TextCharacter character;
    
    @Setter
    @Getter
    protected TerminalRectangle position;
    
    protected GameObject(TextCharacter character, TerminalRectangle position) {
        this.character = character;
        this.position = position;
    }
}
