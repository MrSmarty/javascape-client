package com.javascape;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Client extends Application {

    Stage primaryStage;

    private static ClientGUI gui;

    public User loggedInUser;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(true);

        this.primaryStage = primaryStage;

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

    public void startGui() {
        gui = new ClientGUI();
        //gui.show();
    }

}