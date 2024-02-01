package com.javascape.receivers;

import com.javascape.sensors.Sensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public abstract class Receiver {
    public String uid;
    public String name;
    public String type;
    public int householdID;
    public Sensor[] sensors;
    public int[] values;

    /** Boolean to tell whether or not the receiver is connected */
    public boolean connected;

    transient protected Label tempLabel;
    transient protected ObservableList<Double> internalTemps;

    public Receiver(String ID) {
        internalTemps = FXCollections.<Double>observableArrayList();
        this.uid = ID;
        name = "Receiver";
    };

    public Receiver(String ID, String name, String type) {
        internalTemps = FXCollections.<Double>observableArrayList();
        this.uid = ID;
        this.name = name;
        this.type = type;
    }

    public String getUID() {
        return uid;
    };

    public String getName() {
        return name;
    };

    public void setName(String name) {
        this.name = name;
    };

    @Override
    public String toString() {
        return getName();
    }

    public abstract ReceiverPane getReceiverPane();

    public abstract int[] getValues();

    public abstract void setValue(int pin, int value);

    public abstract void addInternalTemperatureValue(double temperature);

    public abstract void addSensor(Sensor sensor, int pin);

    public abstract void removeSensor(Sensor sensor);

    public abstract Sensor getSensor(int pin);

    public abstract Sensor[] getSensors();

    public double getInternalTemperatureValue() {
        if (internalTemps.size() == 0) {
            return 0;
        }
        return internalTemps.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Receiver) {
            Receiver r = (Receiver) o;
            return r.uid.equals(uid);
        }
        return false;
    }
}
