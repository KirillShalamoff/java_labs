package org.openjfx.Controller;

import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConsoleInputHandler {
    private final Logger logger = LogManager.getLogger();

    public String getCommand() {
        try {
            if (System.in.available() > 0) {
                int key = System.in.read();
                logger.info("registered key: {}", key);
                return switch (key) {
                    case 27      -> "EXIT";
                    case 'a', 'A' -> "LEFT";
                    case 'd', 'D' -> "RIGHT";
                    case 'w', 'W' -> "ROTATE";
                    case 's', 'S' -> "DOWN";
                    case ' ' -> "HARDDROP";
                    default -> "NONE";
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NONE";
    }
}
