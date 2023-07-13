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
    TextCharacter PLAYER_LEFT_SIDE_OF_HEAD = TextCharacter.fromCharacter('(', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_HEAD = TextCharacter.fromCharacter(')', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_LEFT_SIDE_OF_BODY = TextCharacter.fromCharacter('/', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_BODY = TextCharacter.fromCharacter('\\', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter ENDPOINT_RIGHT = TextCharacter.fromCharacter('/', TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter ENDPOINT_LEFT = TextCharacter.fromCharacter('\\', TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
}
