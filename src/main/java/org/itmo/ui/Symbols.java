package org.itmo.ui;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import static com.googlecode.lanterna.Symbols.*;

public interface Symbols {
    
    TextCharacter SPACE = TextCharacter.fromCharacter(' ', TextColor.ANSI.DEFAULT,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter WALL = TextCharacter.fromCharacter('@', TextColor.ANSI.DEFAULT,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter RED_BOX_LEFT_TOP = TextCharacter.fromCharacter(DOUBLE_LINE_TOP_LEFT_CORNER,
        TextColor.ANSI.RED,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter RED_BOX_RIGHT_TOP = TextCharacter.fromCharacter(DOUBLE_LINE_TOP_RIGHT_CORNER,
        TextColor.ANSI.RED,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter RED_BOX_LEFT_BOTTOM = TextCharacter.fromCharacter(DOUBLE_LINE_BOTTOM_LEFT_CORNER,
        TextColor.ANSI.RED,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter RED_BOX_RIGHT_BOTTOM = TextCharacter.fromCharacter(DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
        TextColor.ANSI.RED,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter GREEN_BOX_LEFT_TOP = TextCharacter.fromCharacter(DOUBLE_LINE_TOP_LEFT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter GREEN_BOX_RIGHT_TOP = TextCharacter.fromCharacter(DOUBLE_LINE_TOP_RIGHT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter GREEN_BOX_LEFT_BOTTOM = TextCharacter.fromCharacter(DOUBLE_LINE_BOTTOM_LEFT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter GREEN_BOX_RIGHT_BOTTOM = TextCharacter.fromCharacter(DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    TextCharacter PLAYER_LEFT_SIDE_OF_HEAD = TextCharacter.fromCharacter('(', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_HEAD = TextCharacter.fromCharacter(')', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_LEFT_SIDE_OF_BODY = TextCharacter.fromCharacter('/', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_BODY = TextCharacter.fromCharacter('\\', TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN = TextCharacter.fromCharacter('>',
        TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter PLAYER_RIGHT_SIDE_OF_BODY_LEFT_TURN = TextCharacter.fromCharacter('<',
        TextColor.ANSI.BLUE,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter ENDPOINT_RIGHT_TOP = TextCharacter.fromCharacter(SINGLE_LINE_TOP_LEFT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter ENDPOINT_LEFT_TOP = TextCharacter.fromCharacter(SINGLE_LINE_TOP_RIGHT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter ENDPOINT_RIGHT_BOTTOM = TextCharacter.fromCharacter(SINGLE_LINE_BOTTOM_LEFT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
    TextCharacter ENDPOINT_LEFT_BOTTOM = TextCharacter.fromCharacter(SINGLE_LINE_BOTTOM_RIGHT_CORNER,
        TextColor.ANSI.GREEN,
        TextColor.ANSI.DEFAULT)[0];
    
}
