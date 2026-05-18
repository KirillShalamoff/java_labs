package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    // Параметры складов
    public final int storageBodySize;
    public final int storageEngineSize;
    public final int storageAccessorySize;
    public final int storageAutoSize;

    // Параметры потоков
    public final int accessorySuppliers;
    public final int workers;
    public final int dealers;

    // Логирование
    public final boolean logSale;

    public ConfigReader(String fileName) {
        Properties props = new Properties();

        // Загружаем файл из ресурсов
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException("Не удалось найти файл конфигурации: " + fileName);
            }
            props.load(input);
        } catch (IOException e) {
            logger.error("Ошибка при загрузке конфига: ", e);
            // Если конфиг не загрузился, лучше остановить программу,
            // так как без параметров фабрика не заработает.
            throw new RuntimeException(e);
        }

        // Читаем значения с преобразованием в нужные типы
        this.storageBodySize = Integer.parseInt(props.getProperty("StorageBodySize", "100"));
        this.storageEngineSize = Integer.parseInt(props.getProperty("StorageEngineSize", "100"));
        this.storageAccessorySize = Integer.parseInt(props.getProperty("StorageAccessorySize", "100"));
        this.storageAutoSize = Integer.parseInt(props.getProperty("StorageAutoSize", "100"));

        this.accessorySuppliers = Integer.parseInt(props.getProperty("AccessorySuppliers", "5"));
        this.workers = Integer.parseInt(props.getProperty("Workers", "10"));
        this.dealers = Integer.parseInt(props.getProperty("Dealers", "20"));

        this.logSale = Boolean.parseBoolean(props.getProperty("LogSale", "false"));

        logger.info("Конфигурация успешно загружена из {}", fileName);
    }
}