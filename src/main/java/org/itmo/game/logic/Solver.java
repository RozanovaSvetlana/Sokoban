package org.itmo.game.logic;

import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.itmo.game.map.Map;
import org.itmo.game.objects.Box;
import org.itmo.game.objects.Endpoint;
import org.itmo.game.objects.Player;
import org.itmo.game.objects.Position;
import org.itmo.game.objects.Rectangle;
import org.itmo.game.objects.Wall;

public class Solver {
    
    private ArrayDeque<Pair> queueMapStates;
    private Set<MapState> viewedStates;
    boolean isSolutionFound;
    
    @Getter
    @AllArgsConstructor
    private class Pair {
        MapState mapState;
        List<Direction> path;
    }
    
    /**
     * Finds a solution for the transferred map
     * @param map - solution map
     * @return
     */
    public List<Direction> findSolution(Map map) {
        Game gameLogic = new Game();
        gameLogic.setMap(map);
        if(gameLogic.isExistsBoxNotOnEndpointButInCorner()) {
            return null;
        }
        if (gameLogic.isWin()) {
            return new ArrayList<>();
        }
        isSolutionFound = false;
        queueMapStates = new ArrayDeque<>();
        viewedStates = new HashSet<>();
        MapState fstState = getMapStateFromMap(map);
        queueMapStates.add(new Pair(fstState, new ArrayList<>()));
        viewedStates.add(fstState);
        while (!isSolutionFound && !queueMapStates.isEmpty()) {
            Pair pair = queueMapStates.poll();
            MapState state = pair.getMapState();
            List<Box> boxes = state.getBoxPositions().stream()
                .map((x) -> new Box(new Rectangle(new Position(x), 1, 1))).toList();
            stepInBfs(state.playerPosition, boxes, map, gameLogic, LEFT, pair.getPath());
            if(isSolutionFound) {
                return queueMapStates.getLast().getPath();
            }
            stepInBfs(state.playerPosition, boxes, map, gameLogic, DOWN, pair.getPath());
            if(isSolutionFound) {
                return queueMapStates.getLast().getPath();
            }
            stepInBfs(state.playerPosition, boxes, map, gameLogic, RIGHT, pair.getPath());
            if(isSolutionFound) {
                return queueMapStates.getLast().getPath();
            }
            stepInBfs(state.playerPosition, boxes, map, gameLogic, UP, pair.getPath());
            if(isSolutionFound) {
                return queueMapStates.getLast().getPath();
            }
        }
        return null;
    }
    
    /**
     * Takes one step in the indicated direction on the passed map
     *
     * @param gameLogic - business logic
     * @param map - the map on which to step
     * @param direction - direction in which to step
     * @return true - if the step is possible, false - otherwise
     */
    private boolean takeStep(Game gameLogic, Map map, Direction direction) {
        gameLogic.setMap(map);
        return gameLogic.step(direction);
    }
    
    /**
     * Responsible for a step in the BFS. If a step in the specified direction was successful
     * and the found state has not yet been visited, adds the found state to the queue
     * for traversal. If the found state has at least one deadlock box (a box that is in a corner
     * and not on an endpoint), then the state will not be added to the queue.
     *
     * @param playerPosition - player position for performing a step
     * @param boxes - pitch box list
     * @param map - the map to be stepped on (only endpoints and walls are taken into account)
     * @param gameLogic - business logic
     * @param direction - direction in which to step
     * @param path - the sequence of steps leading to the transmitted state
     */
    private void stepInBfs(Position playerPosition, List<Box> boxes, Map map, Game gameLogic,
                           Direction direction, List<Direction> path) {
        Map mapForStep = getMapFromMapState(boxes.stream().map(Box::new)
                .collect(Collectors.toList()),
            new Player(new Rectangle(new Position(playerPosition), 1, 1)),
            map.getEndpoints(), map.getWalls());
        if (takeStep(gameLogic, mapForStep, direction) &&
            !gameLogic.isExistsBoxNotOnEndpointButInCorner()) {
            MapState stateAfterStep = getMapStateFromMap(mapForStep);
            if (!viewedStates.contains(stateAfterStep)) {
                isSolutionFound = gameLogic.isWin();
                ArrayList<Direction> updatePath = new ArrayList<>(path);
                updatePath.add(direction);
                viewedStates.add(stateAfterStep);
                queueMapStates.add(new Pair(stateAfterStep, updatePath));
            }
        }
    }
    
    /**
     * Converts Map to MapState
     * @param map - Map for convert
     * @return MapState
     */
    private MapState getMapStateFromMap(Map map) {
        return new MapState(new Position(map.getPlayer().getRectangle().getPosition()),
            map.getBoxes().stream().map((x) -> x.getRectangle().getPosition())
                .collect(Collectors.toSet()));
    }
    
    /**
     * Converts MapState to Map
     *
     * @param boxes - box list
     * @param player - player
     * @param endpoints - endpoint list
     * @param walls - wall list
     * @return Map from MapState
     */
    private Map getMapFromMapState(List<Box> boxes, Player player, List<Endpoint> endpoints,
                                   List<Wall> walls) {
        return Map.builder().setBoxes(boxes).setPlayer(player).setEndpoint(endpoints)
            .setWalls(walls).build();
    }
    
}
