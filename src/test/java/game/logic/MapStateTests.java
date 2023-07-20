package game.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.itmo.game.logic.MapState;
import org.itmo.game.objects.Position;
import org.junit.jupiter.api.Test;

public class MapStateTests {
    
    @Test
    public void equalsMapState() {
        Position player = generatePosition();
        HashSet<Position> boxes = createSetPosition(5);
        MapState fstMap = createMapState(player, boxes);
        MapState sndMap = createMapState(new Position(player),
            boxes.stream().map(Position::new).collect(Collectors.toSet()));
        assertEquals(fstMap.hashCode(), sndMap.hashCode());
        assertEquals(fstMap, sndMap);
    }
    
    @Test
    public void notEqualsMapState() {
        MapState fstMap = createMapState(generatePosition(), createSetPosition(5));
        MapState sndMap = createMapState(generatePosition(), createSetPosition(5));
        assertNotEquals(fstMap.hashCode(), sndMap.hashCode());
        assertNotEquals(fstMap, sndMap);
    }
    
    public MapState createMapState(Position player, Set<Position> boxes) {
        return new MapState(player, boxes);
    }
    
    public Position generatePosition() {
        Random random = new Random();
        return new Position(random.nextInt(), random.nextInt());
    }
    
    public HashSet<Position> createSetPosition(int number) {
        HashSet<Position> objects = new HashSet<>(number);
        for(int i = 0; i < number;) {
            Position position = generatePosition();
            if(objects.add(position)) {
                i++;
            }
        }
        return objects;
    }
}
