package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


 class PropertiesResourceManager {

    private Properties properties = new Properties();

    /**
     * Default Constructor
     */
    public PropertiesResourceManager() {
        properties = new Properties();
    }

    /**
     * Constructor
     *
     * @param resourceName Name of resource
     */
    public PropertiesResourceManager(final String resourceName) {
        properties = appendFromResource(properties, resourceName);
    }

    /**
     * Конструктор для создания одного объекта из двух properties-файлов
     *
     * @param defaultResourceName Default Resource Name
     * @param resourceName        Resource Name
     */
    public PropertiesResourceManager(final String defaultResourceName, final String resourceName) {
        this(defaultResourceName);
        properties = appendFromResource(new Properties(properties), resourceName);
    }

    /**
     * Объединение двух properties-файлов (параметры из 2-го файла переопределяют параметры из 1-го)
     *
     * @param objProperties Properties
     * @param resourceName  Resource Name
     * @return Properties
     */
    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }

    /**
     * Получение значения параметра по ключу
     *
     * @param key Key
     * @return Value
     */
    public String getProperty(final String key) {
        return (System.getProperty(key) == null) ? properties.getProperty(key) : System.getProperty(key);
    }

    /**
     * Получение значения параметра по ключу
     *
     * @param key          Key
     * @param defaultValue Default Value
     * @return Value
     */
    public String getProperty(final String key, final String defaultValue) {
        return (getProperty(key) == null) ? defaultValue : getProperty(key);
    }

    /**
     * Sets the property
     *
     * @param key   Key
     * @param value Value
     */
    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }
}

