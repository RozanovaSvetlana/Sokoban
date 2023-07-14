package org.itmo.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import org.itmo.game.logic.Direction;
import org.itmo.game.logic.Game;

/**
 * A class that responds to the user's click in an appropriate way
 */
public class Controller {
    
    private static final Game gameLogic = new Game();
    private String fileName;
    
    public Controller(String fileName) {
        this.fileName = fileName;
    }
    
    public void play() throws IOException {
        if(startGame()) {
            gameLogic.toGameWindow(fileName);
            while (true) {
                KeyStroke input = gameLogic.getKeyPressed();
                if(input != null) {
                    switch (input.getKeyType()) {
                        case ArrowUp -> {
                            gameLogic.takeStep(Direction.UP);
                        }
                        case ArrowRight -> {
                            gameLogic.takeStep(Direction.RIGHT);
                        }
                        case ArrowDown -> {
                            gameLogic.takeStep(Direction.DOWN);
                        }
                        case ArrowLeft -> {
                            gameLogic.takeStep(Direction.LEFT);
                        }
                        case Escape -> {
                            gameLogic.closeWindow();
                            return;
                        }
                        case Character -> {
                            Character key = input.getCharacter();
                            switch (key) {
                                case 'w' | 'ц' -> {
                                    gameLogic.takeStep(Direction.UP);
                                }
                                case 'd' | 'в' -> {
                                    gameLogic.takeStep(Direction.RIGHT);
                                }
                                case 's' | 'ы' -> {
                                    gameLogic.takeStep(Direction.DOWN);
                                }
                                case 'a' | 'ф' -> {
                                    gameLogic.takeStep(Direction.LEFT);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean startGame() throws IOException {
        gameLogic.toLogoWindow();
        while (true) {
            KeyStroke input = gameLogic.getKeyPressed();
            switch (input.getKeyType()) {
                case Enter -> {
                    gameLogic.closeWindow();
                    return true;
                }
                case Escape -> {
                    gameLogic.closeWindow();
                    return false;
                }
            }
        }
    }
}
