package com.javascape;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javascape.gsonDeserializers.*;
import com.javascape.receivers.*;
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

    public static Receiver deserializeReceiver(String json) {
        return gson.fromJson(json, Receiver.class);
    }

    public static String serializeReceiver(Receiver receiver) {
        return gson.toJson(receiver).replaceAll("\n", "");
    }

    public static ObservableList<?> deserializeObservable(String json) {
        return gson.fromJson(json, ObservableList.class);
    }
}
