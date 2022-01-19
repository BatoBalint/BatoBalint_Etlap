package com.example.etlap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private TableView<Etel> menuTable;
    @FXML
    private Label descriptionTxt;
    @FXML
    private Button newFoodBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<Etel, String> nameCol;
    @FXML
    private TableColumn<Etel, String> categoryCol;
    @FXML
    private TableColumn<Etel, Integer> priceCol;

    @FXML
    public void newFoodBtnClick() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApp.class.getResource("add-food-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Étel felvétel");
            stage.setScene(scene);
            stage.setOnCloseRequest(windowEvent -> {test();});
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBtnClick() {

    }

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        Etel e = new Etel("Leves", "leves", "fincsamincsa", 650);
        menuTable.getItems().add(e);
    }

    private void test() {
        new Alert(Alert.AlertType.NONE, "test", ButtonType.CLOSE).show();
    }
}