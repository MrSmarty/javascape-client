package com.javascape;

import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The thread connection for the client */
public class ClientThread extends Thread {
    protected Socket socket;

    /** The commands to send the server */
    public ObservableList<String> commands = FXCollections.observableArrayList();

    public void addCommand(String command) {
        Logger.debug("Adding command...");
        commands.add(command);
    }

    public void addCommand(String command, int index) {
        Logger.debug("Adding command to index " + index + "...");
        commands.add(index, command);
    }
}
