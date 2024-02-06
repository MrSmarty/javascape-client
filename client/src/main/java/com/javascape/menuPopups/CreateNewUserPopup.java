package com.javascape.menuPopups;

import java.util.ArrayList;

import com.javascape.Client;
import com.javascape.Permissions;
import com.javascape.Settings;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateNewUserPopup {

    public CreateNewUserPopup() {

        Stage popupStage = new Stage();
        popupStage.setTitle("Create New User");

        GridPane g = new GridPane();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        TextField passwordField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label adminLabel = new Label("Permissions Level:");
        ChoiceBox<String> adminDropdown = new ChoiceBox<String>();
        ArrayList<String> temp = Permissions.getPermissionsList();

        for (String s : temp) {
            if (Permissions.toInt(s) >= Client.loggedInUser.getPermissionsLevel()) {
                adminDropdown.getItems().add(s);
            }
        }

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("errorText");

        Button submit = new Button("Done");

        submit.setOnAction(e -> {
            if (usernameField.textProperty().getValue() != "" && passwordField.textProperty().getValue() != ""
                    && emailField.textProperty().getValue() != "") {
                String response = Client.getThread()
                        .awaitResponse(String.format("createUser %s %s %s %s", usernameField.textProperty().getValue(),
                                passwordField.textProperty().getValue(), Permissions.toInt(adminDropdown.getValue()),
                                emailField.textProperty().getValue()));
                if (response.equals("true")) {
                    popupStage.close();
                } else if (response.equals("false")) {
                    errorLabel
                            .setText("Error: the email " + emailField.textProperty().getValue() + " is already in use");
                } else {
                    errorLabel.setText("Error creating new user");
                }
            } else {
                errorLabel.setText("Error: all fields must be filled out");
            }
        });

        Button cancel = new Button("Cancel");

        cancel.setOnAction(e -> {
            popupStage.close();
        });

        g.add(usernameLabel, 0, 0);
        g.add(usernameField, 1, 0);
        g.add(passwordLabel, 0, 1);
        g.add(passwordField, 1, 1);
        g.add(emailLabel, 0, 2);
        g.add(emailField, 1, 2);
        g.add(adminLabel, 0, 3);
        g.add(adminDropdown, 1, 3);
        g.add(errorLabel, 0, 4, 2, 1);
        g.add(submit, 0, 5);
        g.add(cancel, 1, 5);

        Scene s = new Scene(g);
        s.getStylesheets().add(getClass().getResource("/stylesheets/main-" + Settings.theme + ".css").toExternalForm());

        popupStage.setScene(s);

        popupStage.show();

    }
}
