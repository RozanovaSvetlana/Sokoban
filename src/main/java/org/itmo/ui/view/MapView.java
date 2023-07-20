package org.itmo.ui.view;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.itmo.game.map.Map;

@Getter
public class MapView {
    private int width;
    private int height;
    private List<GameObjectView> walls;
    private List<GameObjectView> endpoints;
    private List<GameObjectView> boxes;
    private GameObjectView player;
    
    public MapView(Map map, int rowShift, int columnShift) {
        width = map.getWidth() * 2;
        height = map.getHeight() * 2;
        walls = map.getWalls().stream().map((x) -> new WallView(x, rowShift,
            columnShift)).collect(Collectors.toList());
        endpoints = map.getEndpoints().stream().map((x) -> new EndpointView(x, rowShift,
            columnShift)).collect(Collectors.toList());
        boxes = map.getBoxes().stream().map((x) -> new BoxView(x, rowShift,
            columnShift)).collect(Collectors.toList());
        player = new PlayerView(map.getPlayer(), rowShift, columnShift);
    }
    
}
