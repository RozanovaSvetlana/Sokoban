package org.itmo.game.objects.jsonObjects;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.itmo.game.objects.Rectangle;

/**
 * Class for converting a map to json and vice versa
 */

@Getter
@Setter
public class JsonMap {
    private int width;
    private int height;
    private List<Rectangle> walls;
    private List<Rectangle> boxes;
    private List<Rectangle> endpoints;
    private Rectangle player;
}
