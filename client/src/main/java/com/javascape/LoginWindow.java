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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow {

    Stage primaryStage;
    Client client;

    GridPane g;

    Text title;

    Label addressLabel;
    TextField addressField;

    Label portLabel;
    TextField portField;

    Label emailLabel;
    TextField emailField;

    Label errorLabel;

    Label passwordLabel;
    TextField passwordField;

    Button loginButton;

    /** Initializes the login window */
    public LoginWindow(Stage primaryStage, Client client) {
        this.primaryStage = primaryStage;
        this.client = client;

        primaryStage.setTitle("JavaScape Client - " + Settings.version);
        primaryStage.setScene(setupLoginScreen());
    }

    /**
     * Builds the login window
     * 
     * @return The scene for the login window
     */
    private Scene setupLoginScreen() {
        VBox vbox = new VBox();
        title = new Text("JavaScape Client");
        title.getStyleClass().addAll("title", "textClass");
        vbox.getChildren().add(title);

        g = new GridPane();
        g.getStyleClass().add("gridPane");
        vbox.getChildren().add(g);

        addressLabel = new Label("IP:");
        addressLabel.getStyleClass().add("textClass");
        addressField = new TextField();
        addressField.getStyleClass().add("textField");

        portLabel = new Label("Port:");
        portLabel.getStyleClass().add("textClass");
        portField = new TextField("19");
        portField.idProperty().set("portField");
        portField.getStyleClass().add("textField");

        emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("textClass");
        emailField = new TextField();
        emailField.getStyleClass().add("textField");

        passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("textClass");
        passwordField = new TextField();
        passwordField.getStyleClass().add("textField");

        errorLabel = new Label("");
        errorLabel.getStyleClass().addAll("errorLabel", "errorText");

        loginButton = new Button("Login");
        loginButton.idProperty().set("loginButton");

        loginButton.setOnAction(e -> loginButtonClicked(emailField.getText(), passwordField.getText()));

        g.add(addressLabel, 0, 0);
        g.add(addressField, 1, 0);
        g.add(portLabel, 2, 0);
        g.add(portField, 3, 0);
        g.add(emailLabel, 0, 1);
        g.add(emailField, 1, 1);
        g.add(passwordLabel, 0, 2);
        g.add(passwordField, 1, 2);
        g.add(errorLabel, 0, 3, 4, 1);
        g.add(loginButton, 0, 4);

        Scene scene = new Scene(vbox, 600, 400);

        scene.getStylesheets().addAll(getClass().getResource("/stylesheets/login.css").toExternalForm(),
                getClass().getResource("/stylesheets/main-" + Settings.theme + ".css").toExternalForm());

        // Allows the user to press enter to login
        scene.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER"))
                loginButtonClicked(emailField.getText(), passwordField.getText());
        });

        return scene;
    }

    /** Processes the login info */
    private void loginButtonClicked(String email, String password) {
        if (addressField.getText().equals("") || portField.getText().equals("")) {
            errorLabel.setText("Please enter an address and port");
            return;
        } else if (email.equals("") || password.equals("")) {
            errorLabel.setText("Please enter an email and password");
            return;
        }
        try {
            Socket socket = new Socket(addressField.getText(), Integer.parseInt(portField.getText()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            String[] input = in.readLine().split(" ");
            System.out.println(input[0]);

            if (input[0].equals("getInfo")) {
                out.println("info client " + email + " " + password);
                out.flush();
            }

            input = in.readLine().split(" ");
            System.out.println(input[1]);
            if (input[0].equals("loginStatus")) {
                if (input[1].equals("true")) {

                    out.println("getUserInfo " + email);
                    out.flush();

                    String temp = in.readLine();

                    System.out.println(temp);

                    Client.loggedInUser = DataHandler.deserializeUser(temp);

                    client.startClientThread(new ClientThread(socket, in, out));

                    System.out.println("Logged in");

                    client.loggedIn();

                } else {
                    System.out.println("Login failed");
                    errorLabel.setText("Invalid user credentials");
                }
            } else {
                System.out.println("Error logging in");
                errorLabel.setText("Error logging in");
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server");
            errorLabel.setText("Error connecting to server");
        }
    }

}
