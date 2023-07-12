package org.itmo.game.map;

import java.util.List;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.Endpoint;
import org.itmo.game.objects.Player;
import org.itmo.game.objects.Wall;

public class Map {
    
    List<Wall> walls;
    List<Box> boxes;
    List<Endpoint> endpoints;
    
    Player player;
    
    private Map() {}
    
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
            //TODO
            return new Map();
        }
    }
}
