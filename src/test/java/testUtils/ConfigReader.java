package testUtils;

import java.util.Properties;

// Importing from your custom JAR
import com.automation.utils.config.PropertiesLoader;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    /**
     * Loads properties using the reusable JAR utility
     */
    public static void initializeProperties() {
        // We pass the path to the JAR's utility method
        properties = PropertiesLoader.loadProperties(CONFIG_PATH);
    }

    public static String getProperty(String key) {
        if (properties == null) {
            initializeProperties();
        }
        // Using the helper method from the JAR to handle missing keys gracefully
        return PropertiesLoader.getPropertyWithDefault(properties, key, "Property not found");
    }
}