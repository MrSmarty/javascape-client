package com.javascape;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javascape.gsonDeserializers.*;
import com.javascape.recievers.*;
import com.javascape.sensors.Sensor;

import javafx.collections.ObservableList;

public class DataHandler {
    /** The GSON object to be used for saving and loading the JSON */
    private static Gson gson = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
            .registerTypeAdapter(ObservableList.class, new ObservableListDeserializer())
            .registerTypeAdapter(Sensor.class, new SensorDeserializer())
            .disableHtmlEscaping().create();

    public static User deserializeUser(String json) {
        return gson.fromJson(json, User.class);
    }

    public static String serializeUser(User user) {

        return gson.toJson(user).replaceAll("\n", "");
    }

    public static Reciever deserializeReciever(String json) {
        return gson.fromJson(json, Reciever.class);
    }

    public static String serializeReciever(Reciever reciever) {
        return gson.toJson(reciever).replaceAll("\n", "");
    }
}
