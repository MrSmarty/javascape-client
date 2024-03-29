package com.javascape.menuPopups;

import com.javascape.Permissions;
import com.javascape.Settings;

import java.util.ArrayList;

import com.javascape.Client;
import com.javascape.DataHandler;
import com.javascape.User;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditUserPopup {
    public EditUserPopup() {

        Stage popupStage = new Stage();
        popupStage.setTitle("Edit User");

        GridPane g = new GridPane();

        ChoiceBox<User> dropdown = new ChoiceBox<User>();
        @SuppressWarnings("unchecked")
        ObservableList<User> userList = (ObservableList<User>) DataHandler
                .deserializeObservable(Client.getThread().awaitResponse("getUserList"));

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getPermissionsLevel() <= Client.loggedInUser.getPermissionsLevel()) {
                userList.remove(i);
            }
        }

        dropdown.getItems().addAll(userList);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label adminLabel = new Label("Permissions level:");

        ChoiceBox<String> adminDropdown = new ChoiceBox<String>();
        ArrayList<String> temp = Permissions.getPermissionsList();

        for (String s : temp) {
            if (Permissions.toInt(s) >= Client.loggedInUser.getPermissionsLevel()) {
                adminDropdown.getItems().add(s);
            }
        }

        Button save = new Button("Save");

        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {

            User u = dropdown.getValue();

            if (Client.getThread()
                    .awaitResponse(String.format("editUser %s %s %s %s %s", u.getEmail(),
                            usernameField.textProperty().getValue(),
                            passwordField.textProperty().getValue(), Permissions.toInt(adminDropdown.getValue()),
                            emailField.textProperty().getValue()))
                    .equals("true")) {
                u.setUsername(usernameField.textProperty().getValue());
                u.setPassword(passwordField.textProperty().getValue());
                u.setEmail(emailField.textProperty().getValue());
                u.setPermissions(Permissions.toInt(adminDropdown.getValue()));
                popupStage.close();
            }
        });

        cancel.setOnAction(e -> {
            popupStage.close();
        });

        dropdown.setOnAction(e -> {
            usernameField.textProperty().set(dropdown.getValue().getUsername());
            passwordField.textProperty().set(dropdown.getValue().getPassword());
            emailField.textProperty().set(dropdown.getValue().getEmail());
            adminDropdown.valueProperty().set(Permissions.toString(dropdown.getValue().getPermissionsLevel()));
        });

        g.add(dropdown, 0, 0, 2, 1);
        g.add(usernameLabel, 0, 1);
        g.add(usernameField, 1, 1);
        g.add(passwordLabel, 0, 2);
        g.add(passwordField, 1, 2);
        g.add(emailLabel, 0, 3);
        g.add(emailField, 1, 3);
        g.add(adminLabel, 0, 4);
        g.add(adminDropdown, 1, 4);
        g.add(save, 0, 5);
        g.add(cancel, 1, 5);

        Scene scene = new Scene(g);
        scene.getStylesheets()
                .add(getClass().getResource("/stylesheets/main-" + Settings.theme + ".css").toExternalForm());

        popupStage.setScene(scene);
        popupStage.show();
    }
}
