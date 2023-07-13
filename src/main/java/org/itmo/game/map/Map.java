package org.itmo.game.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.TerminalRectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.Endpoint;
import org.itmo.game.objects.GameObject;
import org.itmo.game.objects.Player;
import org.itmo.game.objects.Wall;
import org.itmo.game.objects.jsonObjects.JsonMap;
import org.itmo.game.objects.jsonObjects.JsonObject;
import org.itmo.utils.FileUtils;

@Getter
public class Map {
    
    private int width;
    private int height;
    private List<Wall> walls = new ArrayList<>();
    private List<Box> boxes = new ArrayList<>();
    private List<Endpoint> endpoints = new ArrayList<>();
    
    Player player;
    
    private Map() {}
    
    public static MapBuilder builder() {
        return new MapBuilder();
    }
    
    private Map(JsonMap map) {
        fromJsonMapToMap(map);
    }
    
    private void fromJsonMapToMap(JsonMap jsonMap) {
        width = jsonMap.getWidth();
        height = jsonMap.getHeight();
        jsonMap.getWalls().stream().map((x) -> new Wall(x.terminalRectanglePosition()))
            .forEach(walls::add);
        jsonMap.getBoxes().stream().map((x) -> new Box(x.terminalRectanglePosition()))
            .forEach(boxes::add);
        jsonMap.getEndpoints().stream().map((x) -> new Endpoint(x.terminalRectanglePosition()))
            .forEach(endpoints::add);
        player = new Player(jsonMap.getPlayer().terminalRectanglePosition());
    }
    
    public String fromMapToJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(fromMapToJsonMap());
    }
    
    private JsonMap fromMapToJsonMap() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.setWidth(width);
        jsonMap.setHeight(height);
        jsonMap.setEndpoints(gameObjectListToJsonObjectList(endpoints));
        jsonMap.setBoxes(gameObjectListToJsonObjectList(boxes));
        jsonMap.setWalls(gameObjectListToJsonObjectList(walls));
        jsonMap.setPlayer(gameObjectToJsonObject(player));
        return jsonMap;
    }
    
    private List<JsonObject> gameObjectListToJsonObjectList(List<? extends GameObject> objects) {
        return objects.stream().map(this::gameObjectToJsonObject).collect(Collectors.toList());
    }
    
    private JsonObject gameObjectToJsonObject(GameObject object) {
        TerminalRectangle position = object.getPosition();
        return new JsonObject(position.x, position.y, position.width, position.height);
    }
    
    public static class MapBuilder {
        
        private String fileName = "";
        private int numberBoxes = 0;
        
        /**
         * sets the path for the level file
         * @param fileName
         * @return
         */
        public MapBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        public MapBuilder setNumberBoxes(int amount) {
            this.numberBoxes = amount;
            return this;
        }
        
        public Map build() {
            if (!fileName.equals("")) {
                return buildFromFile();
            }
            //TODO
            return new Map();
        }
        
        private Map buildFromFile() {
            if (fileName.endsWith(".json")) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonMap jsonMap = objectMapper.readValue(FileUtils.getAllFileAsString(fileName),
                        JsonMap.class);
                    return new Map(jsonMap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //TODO
                return null;
            }
        }
        
    }
}
