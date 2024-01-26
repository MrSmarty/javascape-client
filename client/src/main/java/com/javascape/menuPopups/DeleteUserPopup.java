package com.javascape.menuPopups;

import com.javascape.Client;
import com.javascape.DataHandler;
import com.javascape.User;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DeleteUserPopup {
    public DeleteUserPopup(Client client) {

        Stage popupStage = new Stage();
        popupStage.setTitle("Delete User");

        

        GridPane g = new GridPane();

        @SuppressWarnings("unchecked")
        ObservableList<User> userList = (ObservableList<User>) DataHandler.deserializeObservable(client.getThread().awaitResponse("getUserList"));
        userList.remove(Client.loggedInUser);

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getPermissionsLevel() <= Client.loggedInUser.getPermissionsLevel()) {
                userList.remove(i);
            }
        }

        if (userList.size() == 0) {
            popupStage.close();
            return;
        }

        ChoiceBox<User> dropdown = new ChoiceBox<User>(userList);


        Button delete = new Button("Delete User");
        Button close = new Button("Close");

        delete.setOnAction(e -> {
            client.getThread().addCommand(("deleteUser " + dropdown.getValue().getEmail()));
            popupStage.close();
        });

        close.setOnAction(e -> {
            popupStage.close();
        });


        g.add(dropdown, 0, 0, 1, 1);
        g.add(delete, 0, 1);
        g.add(close, 1, 1);

        Scene scene = new Scene(g);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/main.css").toExternalForm());


        popupStage.setScene(scene);

        popupStage.show();
    }
}
