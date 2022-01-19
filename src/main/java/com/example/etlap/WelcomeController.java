package com.example.etlap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private DB db;
    private List<Etel> etelList;

    @FXML
    public void newFoodBtnClick() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApp.class.getResource("add-food-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Étel felvétel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBtnClick() {

    }

    @FXML
    private void menuTableClicked() {
        descriptionTxt.setText(menuTable.getSelectionModel().getSelectedItem().getDesc());
    }

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            db = new DB();
            etelList = db.getEtlap();
            for (Etel e: etelList) {
                menuTable.getItems().add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        new Alert(Alert.AlertType.NONE, "test", ButtonType.CLOSE).show();
    }
}