package com.javascape.receivers;

import com.javascape.sensors.analog.Sensor;

import java.util.ArrayList;

import com.javascape.Client;
import com.javascape.ClientThread;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PicoW extends Receiver {

    transient private ClientThread thread = Client.getThread();

    transient private CheckBox[] checkBoxes;

    public boolean gpioExpanded = false, sensorExpanded = false;

    public PicoW(String uid) {
        super(uid, "Pico W", "PiPicoW");
        sensors = new Sensor[3];
        gpio = new GPIO[26];
    }

    public PicoW(String uid, String name, String type) {
        super(uid, name, type);
        sensors = new Sensor[3];
        gpio = new GPIO[26];
    }

    public ReceiverPane getReceiverPane() {
        if (checkBoxes == null)
            checkBoxes = new CheckBox[26];
        ReceiverPane g = new ReceiverPane(uid, this);

        Label nameLabel = new Label(super.getName());
        TextField nameField = new TextField(super.getName());
        nameField.visibleProperty().set(false);
        nameLabel.cursorProperty().setValue(Cursor.HAND);

        nameField.setOnAction(e -> {
            nameLabel.setText(nameField.getText());
            nameField.visibleProperty().set(false);
            nameLabel.visibleProperty().set(true);
            super.setName(nameField.getText());
        });

        nameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    nameField.visibleProperty().set(true);
                    nameLabel.visibleProperty().set(false);
                }
            }
        });

        g.add(nameLabel, 0, 0);
        g.add(nameField, 0, 0);

        g.add(new Label("UID: " + super.getUID()), 1, 0);
        g.add(new Label("Temperature: "), 2, 0);
        tempLabel = new Label();
        if (internalTemps == null)
            internalTemps = FXCollections.<Double>observableArrayList();
        internalTemps.addListener((ListChangeListener.Change<? extends Double> change) -> {
            Platform.runLater(new Runnable() {
                public void run() {
                    tempLabel.setText(String.format("%.2f˚ Celcius", getInternalTemperatureValue()));
                }
            });
        });

        g.add(tempLabel, 3, 0);

        GridPane buttonPane = new GridPane();

        for (int i = 0; i < 26; i++) {
            gpio[i] = new GPIO(uid, i);

            if (i < 13) {
                buttonPane.add(gpio[i].getUI(), 0, i);
            } else {
                buttonPane.add(gpio[i].getUI(), 1, i - 13);
            }

            gpio[i].setConnectionStatus(connected);

        }

        TitledPane gpioPane = new TitledPane("GPIO", buttonPane);
        gpioPane.expandedProperty().set(gpioExpanded);

        gpioPane.setOnMouseClicked(e -> {
            gpioExpanded = gpioPane.expandedProperty().get();
        });

        g.add(gpioPane, 0, 1);

        VBox sensorVBox = new VBox();

        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null) {
                sensorVBox.getChildren().add(sensors[i].getSensorPane());
            } else {
                // int tempI = i;
                HBox h = new HBox();
                h.getChildren().add(new Label("ADC " + i + " is empty"));
                Button addSensorButton = new Button("Add Sensor");
                h.getChildren().add(addSensorButton);

                addSensorButton.setOnAction(e -> {
                    // FIXME: new AddSensorPopup(this, tempI);
                });

                sensorVBox.getChildren().add(h);
            }
        }

        TitledPane sensorPane = new TitledPane("Sensors", sensorVBox);
        sensorPane.expandedProperty().set(sensorExpanded);

        sensorPane.setOnMouseClicked(e -> {
            sensorExpanded = sensorPane.expandedProperty().get();
        });

        g.add(sensorPane, 0, 2);

        return g;
    }

    public ClientThread getThread() {
        return thread;
    }

    public int[] getValues() {
        int[] values = new int[26];
        for (int i = 0; i < gpio.length; i++) {
            values[i] = gpio[i].value;
        }
        return values;
    }

    public void setValue(int pin, int value) {
        checkBoxes[pin].selectedProperty().set(value == 1);
    }

    // private void sendCommand(String message) {
    // Logger.print("Sending Command to " + Client.getThread().getName());
    // Client.getThread().addCommand(message);
    // }

    public void addInternalTemperatureValue(double temperature) {
        internalTemps.add(0, temperature);
        if (internalTemps.size() > 10) {
            internalTemps.remove(internalTemps.size() - 1);
        }
    }

    public void addSensor(Sensor sensor, int pin) {
        sensors[pin] = sensor;
    }

    public void removeSensor(Sensor sensor) {
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] == sensor) {
                sensors[i] = null;
                return;
            }
        }
    }

    public Sensor getSensor(int pin) {
        return sensors[pin];
    }

    public Sensor[] getSensors() {
        return sensors;
    }

    @Override
    public boolean updateReceiver(Receiver newReceiver) {
        if (newReceiver == null) {
            return false;
        }
        name = newReceiver.getName();
        for (int i = 0; i < 26; i++) {
            if (newReceiver.getValues()[i] != gpio[i].value) {
                gpio[i].setValue(newReceiver.getValues()[i]);
            }
        }

        for (int i = 0; i < sensors.length; i++) {
            if (newReceiver.getSensors()[i] != null) {
                if (sensors[i] == null) {
                    sensors[i] = newReceiver.getSensors()[i];
                } else {
                    // sensors[i].updateSensor(newReceiver.getSensors()[i]);
                }
            }
        }

        return true;
    }

    public ArrayList<GPIO> getDigitalSensors() {
        ArrayList<GPIO> digitalSensors = new ArrayList<GPIO>();
        System.out.println("Getting digital sensors");
        for (GPIO g : gpio) {
            if (g.sensor != null) {
                digitalSensors.add(g);
            }
        }
        System.out.println(digitalSensors.size());

        return digitalSensors;
    }

}
