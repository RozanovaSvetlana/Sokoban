package org.itmo.game.map;

import static java.lang.Math.max;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalRectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.Endpoint;
import org.itmo.game.objects.GameObject;
import org.itmo.game.objects.Player;
import org.itmo.game.objects.Wall;
import org.itmo.game.objects.WallsLoader;
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
        width = jsonMap.getWidth() * 2;
        height = jsonMap.getHeight() * 2;
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
        jsonMap.setWidth(width / 2);
        jsonMap.setHeight(height / 2);
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
        return new JsonObject(position.x / 2, position.y / 2, position.width / 2,
            position.height / 2);
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
                try(BufferedReader reader =
                        new BufferedReader(new InputStreamReader(FileUtils.getFile(fileName)))) {
                    Map mp = new Map();
                    mp.height = 0;
                    mp.width = 0;
                    WallsLoader wallsLoader = new WallsLoader();
                    while (reader.ready()) {
                        String line = reader.readLine();
                        mp.width = max(mp.width, line.trim().length());
                        addGameObjectFromString(wallsLoader, mp, line);
                        mp.height += 2;
                    }
                    mp.walls = wallsLoader.getWalls().entrySet().stream().map(entry -> {
                        TerminalPosition wallStart = entry.getValue();
                        TerminalPosition wallEnd = entry.getKey();
                        int width = Math.abs(wallStart.getColumn() - wallEnd.getColumn()) + 2;
                        int height = Math.abs(wallStart.getRow() - wallEnd.getRow()) + 2;
                        return new Wall(
                            new TerminalRectangle(wallStart.getColumn(), wallStart.getRow(), width,
                                height));
                    }).collect(Collectors.toList());
                    return mp;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        private void addGameObjectFromString(WallsLoader wallsLoader, Map mp, String line) {
            List<Integer> gameObjectInString =
                getAllIndexGameObjectInString(line, '+');
            if(!gameObjectInString.isEmpty()) {
                mp.player = new Player(
                    new TerminalRectangle(gameObjectInString.get(0) * 2,
                        mp.height, 2, 2));
            }
            gameObjectInString = getAllIndexGameObjectInString(line, 'x');
            if(!gameObjectInString.isEmpty()) {
                gameObjectInString.forEach((x) -> mp.getBoxes()
                    .add(new Box(
                        new TerminalRectangle(x * 2, mp.height, 2, 2))));
            }
            gameObjectInString = getAllIndexGameObjectInString(line, '.');
            if(!gameObjectInString.isEmpty()) {
                gameObjectInString.forEach((x) -> mp.getEndpoints()
                    .add(new Endpoint(
                        new TerminalRectangle(x * 2, mp.height, 2, 2))));
            }
            addWalls(mp, wallsLoader, line);
        }
        
        private List<Integer> getAllIndexGameObjectInString(String line, char symbol) {
            int index = line.indexOf(symbol);
            List<Integer> allIndex = new ArrayList<>();
            while (index != -1) {
                allIndex.add(index);
                index = line.indexOf(symbol, index + 1);
            }
            return allIndex;
        }
        
        private void addWalls(Map mp, WallsLoader wallsLoader, String line) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '@') {
                    TerminalRectangle position = new TerminalRectangle(i * 2,
                        mp.height, 2, 2);
                    wallsLoader.getWalls(position, i * 2, mp.height);
                }
            }
        }
        
    }
}
