package org.openjfx.Controller;

import java.io.IOException;

public class ConsoleInputHandler {
    public String getCommand() {
        try {
            if (System.in.available() > 0) {
                int key = System.in.read();
                return switch (key) {
                    case 27      -> "EXIT";
                    case 'a', 'A' -> "LEFT";
                    case 'd', 'D' -> "RIGHT";
                    case 'w', 'W' -> "ROTATE";
                    case 's', 'S' -> "DOWN";
                    default -> "NONE";
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NONE";
    }
}
