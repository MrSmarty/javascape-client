package com.javascape;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javascape.chronjob.ChronManager;
import com.javascape.chronjob.Chronjob;
import com.javascape.chronjob.ConditionalJob;
import com.javascape.chronjob.Job;
import com.javascape.gsonDeserializers.*;
import com.javascape.receivers.*;
import com.javascape.sensors.Sensor;

import javafx.collections.ObservableList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataHandler {
    /** The GSON object to be used for saving and loading the JSON */
    private static Gson gson = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
            .registerTypeAdapter(ObservableList.class, new ObservableListDeserializer())
            .registerTypeAdapter(Sensor.class, new SensorDeserializer())
            .disableHtmlEscaping().create();

    private static ChronManager chronManager = new ChronManager();

    public static void ensureStorageDirectory() {
        try {
            Path storagePath = Paths.get(Settings.storageLocation);
            if (!Files.exists(storagePath)) {
                Files.createDirectory(storagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ChronManager getChronManager() {
        return chronManager;
    }

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

    public static String serializeChronjob(Chronjob job) {
        return gson.toJson(job).replaceAll("\n", "");
    }

    public static Chronjob deserializeChronjob(String json) {
        return gson.fromJson(json, Chronjob.class);
    }

    public static String serializeConditional(ConditionalJob job) {
        return gson.toJson(job).replaceAll("\n", "");
    }

    public static ConditionalJob deserializeConditional(String json) {
        return gson.fromJson(json, ConditionalJob.class);
    }

    public static String serializeJob(Job job) {
        return gson.toJson(job).replaceAll("\n", "");
    }

    public static ObservableList<?> deserializeObservable(String json) {
        return gson.fromJson(json, ObservableList.class);
    }
}
