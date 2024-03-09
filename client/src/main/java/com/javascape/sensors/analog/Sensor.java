package com.javascape.sensors.analog;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public abstract class Sensor {
    public String className;

    /** The list of previous recorded values */
    protected transient ObservableList<Object> valueList;

    public String receiverID;

    /** The name of the sensor */
    public String name;

    public int index;

    public Sensor(String receiverID, String name, int index) {
        this.name = name;
        this.receiverID = receiverID;
        this.index = index;
    }

    /** Sets the name of the sensor */
    public void setName(String newName) {
        name = newName;
    }

    /** returns the name of the sensor */
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public abstract void addValue(String value);

    public abstract Double getCurrentValueAsDouble();

    /**
     * Returns the Node of the Sensor.
     * Should include a delete button
     * Can be any type of JavaFX Node, but should most likely be a Pane of some kind.
     * 
     * @return The Javafx Node of the Sensor
     */
    abstract public Node getSensorPane();

    @Override
    public String toString() {
        return name;
    }
}
