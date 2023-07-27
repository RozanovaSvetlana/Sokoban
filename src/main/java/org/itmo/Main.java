package org.itmo;

import static org.itmo.Commands.*;

import java.io.IOException;
import org.itmo.game.Controller;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 3) {
            System.out.println("Not enough arguments in the command");
        } else {
            if (args[0].equals(BASE_COMMAND)) {
                switch (args[1]) {
                    case PLAY -> {
                        new Controller(args[2]).play();
                    }
                    case LOAD_LEVEL -> {
                    
                    }
                    case GENERATE_LEVEL -> {
                    
                    }
                    case SOLVE_LEVEL -> {
                        if(args.length < 4) {
                            System.out.println("Not enough arguments in the command");
                        }
                        if(args[3].equalsIgnoreCase("yes")) {
                            if(args.length < 5) {
                                System.out.println("Not enough arguments in the command");
                            } else {
                                new Controller(args[2]).solve(true, args[4]);
                            }
                        } else if(args[3].equalsIgnoreCase("no")) {
                            new Controller(args[2]).solve(false, "");
                        } else {
                            System.out.println("Incorrect argument! Enter \"yes\" if you want " +
                                               "to save (after that you must specify the path " +
                                               "and name of the output file) and \"no\" otherwise");
                        }
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