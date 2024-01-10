package com.javascape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The thread connection for the client */
public class ClientThread extends Thread {
    protected Socket socket;

    BufferedReader in;
    PrintStream out;

    String input;

    boolean run = true;

    boolean responseRecieved = false;

    /** The commands to send the server */
    public ObservableList<String> commands = FXCollections.observableArrayList();

    public ClientThread(Socket socket, BufferedReader in, PrintStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        Logger.debug("Starting client thread...");
        while (run) {
            try {
                Thread.sleep(1);
                if (commands.size() > 0) {
                    boolean b = false;
                    if (commands.get(0).startsWith("response ")) {
                        commands.set(0, commands.get(0).substring(9));
                        b = true;
                    }
                    Logger.debug("Sending command " + commands.get(0) + "...");
                    out.println(commands.remove(0));
                    out.flush();

                    input = in.readLine();
                    if (b)
                        responseRecieved = true;
                    // Process the input here if there is any

                }
            } catch (Exception e) {
                Logger.error("Error sending command to server: " + e.getMessage());
            }
        }
    }

    public void addCommand(String command) {
        Logger.debug("Adding command...");
        commands.add(command);
    }

    public void addCommand(String command, int index) {
        Logger.debug("Adding command to index " + index + "...");
        commands.add(index, command);
    }

    public String awaitResponse(String command) {
        Logger.debug("Awaiting response...");

        addCommand("response " + command, 0);

        while (responseRecieved == false) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseRecieved = false;

        return input;
    }

    public void quit() {
        Logger.print("Closing client thread...");
        run = false;

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
