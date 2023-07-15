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
                            if(takeStep(Direction.UP)) {
                                return;
                            }
                        }
                        case ArrowRight -> {
                            if(takeStep(Direction.RIGHT)) {
                                return;
                            }
                        }
                        case ArrowDown -> {
                            if(takeStep(Direction.DOWN)) {
                                return;
                            }
                        }
                        case ArrowLeft -> {
                            if(takeStep(Direction.LEFT)) {
                                return;
                            }
                        }
                        case Escape -> {
                            gameLogic.closeWindow();
                            return;
                        }
                        case Character -> {
                            Character key = input.getCharacter();
                            switch (key) {
                                case 'w', 'ц' -> {
                                    if(takeStep(Direction.UP)) {
                                        return;
                                    }
                                }
                                case 'd', 'в' -> {
                                    if(takeStep(Direction.RIGHT)) {
                                        return;
                                    }
                                }
                                case 's', 'ы' -> {
                                    if(takeStep(Direction.DOWN)) {
                                        return;
                                    }
                                }
                                case 'a', 'ф' -> {
                                    if(takeStep(Direction.LEFT)) {
                                        return;
                                    }
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
    
    private boolean takeStep(Direction direction) throws IOException {
        if(gameLogic.takeStep(direction)) {
            while (true) {
                KeyStroke input = gameLogic.getKeyPressed();
                switch (input.getKeyType()) {
                    case Enter, Escape -> {
                        gameLogic.closeWindow();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
