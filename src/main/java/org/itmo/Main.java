package org.itmo;

import static org.itmo.Commands.*;

import java.io.IOException;
import org.itmo.game.Controller;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 2) {
            System.out.println("Not enough arguments in the command");
        } else {
            if (args[0].equals(BASE_COMMAND)) {
                switch (args[1]) {
                    case PLAY -> {
                        new Controller().play();
                    }
                    case LOAD_LEVEL -> {
                    
                    }
                    case GENERATE_LEVEL -> {
                    
                    }
                    case SOLVE_LEVEL -> {
                    
                    }
                    default -> {
                        System.out.println("Unknown command");
                    }
                }
            } else {
                System.out.println("The error command must begin with the word \"sokoban\"");
            }
        }
    }
}