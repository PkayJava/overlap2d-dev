package com.pkayjava.editor.utils;

import java.util.Properties;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class AppConfig {

    public static AppConfig instance;

    public String version;

    public Properties properties;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
            instance.loadProperties();
        }

        return instance;
    }

    private void loadProperties() {
        // this thing just refused to work so I gave up, fuck it.

        /*
        properties = new Properties();
       // Gdx.files
        File file = new File("app.properties");
        if(!file.exists()) {
        	System.out.println("NO FILE");
            file = new File("assets/app.properties");
            System.out.println("NO FILE " + file.getAbsolutePath());
        }

        //Gdx.files.internal(path)
        try {
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            version = properties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        version = "0.1.1";
    }
}
