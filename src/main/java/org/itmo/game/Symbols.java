package org.itmo.game;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import static com.googlecode.lanterna.Symbols.*;

public interface Symbols {
    
    TextCharacter SPACE = TextCharacter.fromCharacter(' ', TextColor.ANSI.DEFAULT,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter WALL = TextCharacter.fromCharacter('@', TextColor.ANSI.DEFAULT,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter RED_BOX = TextCharacter.fromCharacter('#', TextColor.ANSI.RED,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter GREEN_BOX = TextCharacter.fromCharacter('#', TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter PLAYER = TextCharacter.fromCharacter('+', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter ENDPOINT = TextCharacter.fromCharacter(BULLET, TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
}
