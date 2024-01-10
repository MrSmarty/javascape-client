// package com.javascape.menuPopups;

// import com.javascape.Client;
// import com.javascape.User;
// import javafx.collections.ObservableList;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.ChoiceBox;
// import javafx.scene.layout.GridPane;
// import javafx.stage.Stage;

// public class DeleteUserPopup {
//     public DeleteUserPopup(Client client) {

//         Stage popupStage = new Stage();
//         popupStage.setTitle("Delete User");

        

//         GridPane g = new GridPane();

//         //ObservableList<User> userList = Client.getDataHandler().getUserHandler().getAllUsers();
//         //userList.remove(client.loggedInUser);

//         //ChoiceBox<User> dropdown = new ChoiceBox<User>(userList);


//         Button delete = new Button("Delete User");
//         Button close = new Button("Close");

//         delete.setOnAction(e -> {
//             //Client.getDataHandler().getUserHandler().removeUser(dropdown.getValue());
//             popupStage.close();
//         });

//         close.setOnAction(e -> {
//             popupStage.close();
//         });


//         g.add(dropdown, 0, 0, 1, 1);
//         g.add(delete, 0, 1);
//         g.add(close, 1, 1);

//         Scene scene = new Scene(g);
//         scene.getStylesheets().add(getClass().getResource("/stylesheets/main.css").toExternalForm());


//         popupStage.setScene(scene);

//         popupStage.show();
//     }
// }
