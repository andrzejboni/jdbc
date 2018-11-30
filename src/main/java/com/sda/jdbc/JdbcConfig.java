package com.sda.jdbc;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;


@Log
public class JdbcConfig {
    // pod kluczem znajduje sie wartość


    private Properties jdbcProperties;
    public JdbcConfig() {
        this("/jdbc.properties");
    }


    public JdbcConfig(String fileName) {
        jdbcProperties = loadProperties(fileName);
    }

    private Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try {
            // ładujemy z resoruces
            InputStream stream = JdbcConfig.class.getResourceAsStream(filename);

            if (stream == null) {
                throw new IOException("File read error!");
            }
            properties.load(stream);

        } catch (IOException ioe) {
            log.log(Level.SEVERE, "Error", ioe);
        }
        return properties;
    }

    public String getProperty(String name) {
        //załaduj ustawienie o poodanej nazwie
        return jdbcProperties.getProperty(name);
    }
}




