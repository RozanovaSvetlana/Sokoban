package org.itmo.game.objects.jsonObjects;

import com.googlecode.lanterna.TerminalRectangle;
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
public class JsonObject {
    private int x;
    private int y;
    private int width;
    private int height;
    
    public TerminalRectangle terminalRectanglePosition() {
        return new TerminalRectangle(x * 2, y * 2, width * 2, height * 2);
    }
}
