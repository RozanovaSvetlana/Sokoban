package org.itmo.game.map;

import static java.lang.Math.max;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.itmo.game.objects.Position;
import org.itmo.game.objects.Rectangle;
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
    
    /**
     * Get builder for map
     * @return MapBuilder
     */
    public static MapBuilder builder() {
        return new MapBuilder();
    }
    
    private Map(JsonMap map) {
        fromJsonMapToMap(map);
    }
    
    private Map(List<Wall> walls, List<Box> boxes, List<Endpoint> endpoints, Player player) {
        this.walls = walls;
        this.boxes = boxes;
        this.endpoints = endpoints;
        this.player = player;
    }
    
    /**
     * Performs the conversion from jsonMap to a map object
     *
     * @param jsonMap - map description in jsonMap format
     */
    private void fromJsonMapToMap(JsonMap jsonMap) {
        width = jsonMap.getWidth();
        height = jsonMap.getHeight();
        jsonMap.getWalls().stream().map(Wall::new).forEach(walls::add);
        jsonMap.getBoxes().stream().map(Box::new).forEach(boxes::add);
        jsonMap.getEndpoints().stream().map(Endpoint::new).forEach(endpoints::add);
        player = new Player(jsonMap.getPlayer());
    }
    
    /**
     * Performs conversion from a map to a json string
     *
     * @return map description in json format
     * @throws JsonProcessingException
     */
    public String fromMapToJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(fromMapToJsonMap());
    }
    
    /**
     * Performs conversion from map to jsonMap
     * @return map description in jsonMap format
     */
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
    
    /**
     * Converts a list of GameObjects to a list of JsonObjects
     *
     * @param objects - conversion objects
     * @return description of GameObjects in JsonObjects format
     */
    private List<Rectangle> gameObjectListToJsonObjectList(List<? extends GameObject> objects) {
        return objects.stream().map(this::gameObjectToJsonObject).collect(Collectors.toList());
    }
    
    /**
     * Converting a GameObject to a JsonObject
     * @param object - conversion object
     * @return description of GameObject in JsonObject format
     */
    private Rectangle gameObjectToJsonObject(GameObject object) {
        return new Rectangle(object.getRectangle());
    }
    
    public static class MapBuilder {
        
        private String fileName = "";
        private int numberBoxes = 0;
        private List<Box> boxList;
        private List<Endpoint> endpointList;
        private List<Wall> wallList;
        private Player player;
        
        /**
         * sets the path for the level file
         * @param fileName - map file
         * @return MapBuilder
         */
        public MapBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        /**
         * sets box list for map
         * @param boxes - box list
         * @return MapBuilder
         */
        public MapBuilder setBoxes(List<Box> boxes) {
            boxList = boxes;
            return this;
        }
        
        /**
         * sets player for map
         * @param player - player
         * @return MapBuilder
         */
        public MapBuilder setPlayer(Player player) {
            this.player = player;
            return this;
        }
        
        /**
         * sets endpoint list for map
         * @param endpoints - endpoint list
         * @return MapBuilder
         */
        public MapBuilder setEndpoint(List<Endpoint> endpoints) {
            endpointList = endpoints;
            return this;
        }
        
        /**
         * sets wall list for map
         * @param walls - wall list
         * @return MapBuilder
         */
        public MapBuilder setWalls(List<Wall> walls) {
            wallList = walls;
            return this;
        }
        
        /**
         * Builds a map according to the given parameters
         * @return constructed map
         */
        public Map build() {
            if (!fileName.equals("")) {
                return buildFromFile();
            }
            return new Map(wallList, boxList, endpointList, player);
        }
        
        /**
         * Builds a map from a file
         * @return constructed map
         */
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
                        mp.height += 1;
                    }
                    mp.walls = wallsLoader.getWalls().entrySet().stream().map(entry -> {
                        Position wallStart = entry.getValue();
                        Position wallEnd = entry.getKey();
                        int width = Math.abs(wallStart.getX() - wallEnd.getX()) + 1;
                        int height = Math.abs(wallStart.getY() - wallEnd.getY()) + 1;
                        return new Wall(
                            new Rectangle(wallStart.getX(), wallStart.getY(), width,
                                height));
                    }).collect(Collectors.toList());
                    return mp;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        /**
         * Parses the read string to GameObjects
         *
         * @param wallsLoader - wall handler in the string
         * @param mp - map for adding found objects
         * @param line - processing string
         */
        private void addGameObjectFromString(WallsLoader wallsLoader, Map mp, String line) {
            List<Integer> gameObjectInString =
                getAllIndexGameObjectInString(line, '+');
            if(!gameObjectInString.isEmpty()) {
                mp.player = new Player(
                    new Rectangle(gameObjectInString.get(0), mp.height, 1, 1));
            }
            gameObjectInString = getAllIndexGameObjectInString(line, 'x');
            if(!gameObjectInString.isEmpty()) {
                gameObjectInString.forEach((x) -> mp.getBoxes()
                    .add(new Box(new Rectangle(x, mp.height, 1, 1))));
            }
            gameObjectInString = getAllIndexGameObjectInString(line, '.');
            if(!gameObjectInString.isEmpty()) {
                gameObjectInString.forEach((x) -> mp.getEndpoints()
                    .add(new Endpoint(new Rectangle(x, mp.height, 1, 1))));
            }
            addWalls(mp.height, wallsLoader, line);
        }
        
        /**
         * Gets the index of all occurrences of the character in the string to search GameObject
         *
         * @param line - processing string
         * @param symbol - string search character
         * @return index list of all occurrences of the symbol
         */
        private List<Integer> getAllIndexGameObjectInString(String line, char symbol) {
            int index = line.indexOf(symbol);
            List<Integer> allIndex = new ArrayList<>();
            while (index != -1) {
                allIndex.add(index);
                index = line.indexOf(symbol, index + 1);
            }
            return allIndex;
        }
        
        /**
         * Searches for walls in the line
         * @param height - y line coordinate
         * @param wallsLoader - wall handler in the string
         * @param line - processing string
         */
        private void addWalls(int height, WallsLoader wallsLoader, String line) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '@') {
                    Position position = new Position(i, height);
                    wallsLoader.getWalls(position, i, height);
                }
            }
        }
        
    }
}
