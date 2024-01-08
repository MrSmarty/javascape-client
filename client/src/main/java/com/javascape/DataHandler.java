package com.javascape;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javascape.gsonDeserializers.*;
import com.javascape.sensors.Sensor;

import javafx.collections.ObservableList;

public class DataHandler {
    /** The GSON object to be used for saving and loading the JSON */
    private Gson gson = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
            .registerTypeAdapter(ObservableList.class, new ObservableListDeserializer())
            .registerTypeAdapter(Sensor.class, new SensorDeserializer())
            .disableHtmlEscaping().create();

    public static User deserializeUser(String json) {
        System.out.println("testing");
        return new Gson().fromJson(json, User.class);
    }
}
