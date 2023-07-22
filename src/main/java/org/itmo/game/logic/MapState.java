package org.itmo.game.logic;

import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.itmo.game.objects.Position;

@AllArgsConstructor
public class MapState {
    @Getter
    Position playerPosition;
    @Getter
    Set<Position> boxPositions;
    
    @Override
    public int hashCode() {
        return Objects.hash(playerPosition, boxPositions);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return this.playerPosition.equals(((MapState) obj).playerPosition)
               && this.boxPositions.equals(((MapState) obj).boxPositions);
    }
}
