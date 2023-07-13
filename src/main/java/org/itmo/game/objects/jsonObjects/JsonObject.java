package org.itmo.game.objects.jsonObjects;

import com.googlecode.lanterna.TerminalRectangle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        return new TerminalRectangle(x, y, width, height);
    }
}
