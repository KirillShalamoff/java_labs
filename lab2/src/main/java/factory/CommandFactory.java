package factory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import commands.Command;

public class CommandFactory {

    private final Map<String, Command> commands;

    public CommandFactory() {

        this.commands = new HashMap<>();
        loadConfiguration();
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream is = CommandFactory.class.getResourceAsStream("/configuration.properties")) {
            if (is == null) {
                throw new RuntimeException("Конфигурационный файл не найден!");
            }
            props.load(is);

            for (String commandName : props.stringPropertyNames()) {
                String className = props.getProperty(commandName);

                Class<?> clazz = Class.forName(className);

                Command commandInstance = (Command) clazz.getDeclaredConstructor().newInstance();

                commands.put(commandName, commandInstance);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации фабрики: " + e.getMessage(), e);
        }
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
