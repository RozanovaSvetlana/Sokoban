package game.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.googlecode.lanterna.TerminalRectangle;
import org.itmo.game.map.Map;
import org.itmo.utils.FileUtils;
import org.junit.jupiter.api.Test;

public class MapTests {
    
    private static final String jsonFileNameForTest = "src\\\\test\\\\resources\\test_lvl.json";
    
    @Test
    public void buildFromJsonTest() {
        Map map = getMapFromJson();
        assertEquals(7, map.getWidth());
        assertEquals(5, map.getHeight());
        assertEquals(2, map.getBoxes().size());
        assertEquals(6, map.getWalls().size());
        assertEquals(new TerminalRectangle(3, 2, 1, 1),
            map.getPlayer().getPosition());
    }
    
    @Test
    public void convertMapToJsonTest() {
        try {
            assertEquals(FileUtils.getAllFileAsString(jsonFileNameForTest)
                    .replaceAll("\n", "").replaceAll(" ", ""),
                getMapFromJson().fromMapToJsonString());
        } catch (Exception e) {
            fail();
        }
    }
    
    private static Map getMapFromJson() {
        return Map.builder().setFileName(jsonFileNameForTest).build();
    }
}
