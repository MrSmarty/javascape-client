package com.javascape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow {

    Stage primaryStage;

    GridPane g;

    Text title;

    Label addressLabel;
    TextField addressField;

    Label portLabel;
    TextField portField;

    Label emailLabel;
    TextField emailField;

    Label passwordLabel;
    TextField passwordField;

    Button loginButton;

    /** Initializes the login window */
    public LoginWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Builds the login window
     * 
     * @return The scene for the login window
     */
    private Scene setupLoginScreen() {
        g = new GridPane();

        title = new Text("JavaScape Server");

        addressLabel = new Label("IP:");
        addressField = new TextField();

        portLabel = new Label("Port:");
        portField = new TextField("19");

        emailLabel = new Label("Email:");
        emailField = new TextField();

        passwordLabel = new Label("Password:");
        passwordField = new TextField();

        loginButton = new Button("Login");

        loginButton.setOnAction(e -> loginButtonClicked(emailField.getText(), passwordField.getText()));

        g.add(title, 0, 0);
        g.add(addressLabel, 0, 1);
        g.add(addressField, 1, 1);
        g.add(portLabel, 2, 1);
        g.add(portField, 3, 1);
        g.add(emailLabel, 0, 2);
        g.add(emailField, 1, 2);
        g.add(passwordLabel, 0, 3);
        g.add(passwordField, 1, 3);
        g.add(loginButton, 0, 4);

        Scene scene = new Scene(g, 600, 400);

        // Allows the user to press enter to login
        scene.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER"))
                loginButtonClicked(emailField.getText(), passwordField.getText());
        });

        return scene;
    }

    /** Processes the login info */
    private void loginButtonClicked(String email, String password) {
        try {
            Socket socket = new Socket(addressField.getText(), Integer.parseInt(portField.getText()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            

            String input = in.readLine();
            System.out.println(input);

            if (input.startsWith("getInfo")) {
                out.println("client " + email + " " + password);
                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server");
        }
    }

    /** Sets the primaryStage to the login screen */
    public void show() {
        primaryStage.setScene(setupLoginScreen());
    }

}
