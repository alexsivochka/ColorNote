package at;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfVar {

    final private static Logger LOGGER = Logger.getLogger(ConfVar.class);

    private static final ConfVar instance;

    private static String configFilePath = "./src/main/resources/config.properties";

    private static Properties configurationData = new Properties();

    private static void fillMyProperties(Properties properties, String filePath) {
        InputStreamReader input;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath);
            input = new InputStreamReader(fileInputStream, "UTF8");

            // считываем свойства
            properties.load(input);
        } catch (java.io.FileNotFoundException e) {
            LOGGER.info("Ошибка. Файл config.properties не был найден." + filePath, e);
        } catch (java.io.IOException e) {
            LOGGER.info("IO ошибка в пользовательском методе.", e);
        }
    }

    // Метод, извлекающий property
    private static String getProperty(Properties properties, String propertyKey){
        return properties.getProperty(propertyKey);
    }

    // Конфигурационные данные
    public String deviceName;
    public String appiumIP;
    public int appiumPort;


    static {
        fillMyProperties(configurationData, configFilePath);
        instance = new ConfVar();
    }

    private ConfVar() {
        deviceName = getProperty(configurationData, "deviceName");
        appiumIP = getProperty(configurationData, "appiumIP");
        appiumPort = Integer.parseInt(getProperty(configurationData, "appiumPort"));
    }

    //возвращаем инстанс объекта
    public static ConfVar getInstance() {
        return instance;
    }

}

