package com.javascape;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Client extends Application {

    Stage primaryStage;

    //private static ClientGUI gui;

    private static ClientThread thread;

    public static User loggedInUser;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(true);
        primaryStage.resizableProperty().set(false);

        this.primaryStage = primaryStage;

        DataHandler.ensureStorageDirectory();

        // TODO: Autologin
        login();

    }
    
    /** Called once the user has been logged in */
    public void loggedIn() {
        primaryStage.hide();
        startGui();
    }

    private void login() {
        System.out.println("Displaying login window");
        new LoginWindow(primaryStage, this);
        primaryStage.show();
    }

    public void startClientThread(ClientThread thread) {
        Client.thread = thread;
        Thread.ofVirtual().start(Client.getThread());
    }

    public static ClientThread getThread() {
        return thread;
    
    }

    public void startGui() {
        //gui = new ClientGUI(primaryStage, this);
        new ClientGUI(primaryStage, this);
    }

    public void quit() {
        thread.quit();
    }

}