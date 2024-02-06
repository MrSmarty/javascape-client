// package com.javascape.menuPopups;

// import com.javascape.Client;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.GridPane;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class CreateNewHouseholdPopup {
// public CreateNewHouseholdPopup() {
// Stage popupStage = new Stage();
// popupStage.setTitle("Create new household");

// GridPane g = new GridPane();

// Label nameLabel = new Label("Name:");
// TextField nameField = new TextField();

// Label idLabel = new Label("ID:");
// //TODO: Text idText = new Text("" +
// Client.getDataHandler().getHouseholdHandler().getCurrentID());

// Button save = new Button("Save");
// Button cancel = new Button("Cancel");

// save.setOnAction(e -> {
// if (!nameField.textProperty().getValue().equals("")) {
// //TODO:
// Client.getDataHandler().getHouseholdHandler().createHousehold(nameField.textProperty().getValue());
// popupStage.close();
// }
// });

// cancel.setOnAction(e -> {
// popupStage.close();
// });

// g.add(nameLabel, 0, 0);
// g.add(nameField, 1, 0);

// g.add(idLabel, 0, 1);
// //TODO: g.add(idText, 1, 1);

// g.add(save, 2, 0);
// g.add(cancel, 2, 1);

// Scene scene = new Scene(g);
// scene.getStylesheets().add(getClass().getResource("/stylesheets/main-" +
// Settings.theme +".css").toExternalForm());

// popupStage.setScene(scene);
// popupStage.show();
// }
// }
