package org.itmo.game.objects.jsonObjects;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonMap {
    private int width;
    private int height;
    private List<JsonObject> walls;
    private List<JsonObject> boxes;
    private List<JsonObject> endpoints;
    private JsonObject player;
}