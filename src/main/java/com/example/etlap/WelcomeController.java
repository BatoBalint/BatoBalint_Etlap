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
import java.util.Optional;

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
    private Spinner<Integer> percentageSpinner;
    @FXML
    private Spinner<Integer> hufSpinner;
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
            stage.setOnCloseRequest(windowEvent -> loadDataToTable());
            stage.setOnHiding(windowEvent -> loadDataToTable());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBtnClick() {
        int selectedIndex = menuTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            new Alert(Alert.AlertType.NONE, "Elöbb válasz ki egy ételt", ButtonType.CLOSE).show();
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Biztos hogy törölni szeretné az ételt? (" +
                    menuTable.getSelectionModel().getSelectedItem().getName() +
                    ")");
            confirm.setTitle("Étel törlése");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    db.deleteEtel(menuTable.getSelectionModel().getSelectedItem().getId());
                    loadDataToTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void percentageBtnClick() {
        try {
            boolean ok = db.percentageChange(percentageSpinner.getValue());
            if (!ok) {
                new Alert(Alert.AlertType.ERROR, "Valami okbol fogva nem sikerült frissiteni az adatokat", ButtonType.CLOSE).show();
            } else {
                loadDataToTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //new Alert(Alert.AlertType.NONE, precentageSpinner.getValue() + "", ButtonType.CLOSE).show();
    }

    @FXML
    private void hufBtnClick() {
        try {
            boolean ok = db.hufChange(hufSpinner.getValue());
            if (!ok) {
                new Alert(Alert.AlertType.ERROR, "Valami okbol fogva nem sikerült frissiteni az adatokat", ButtonType.CLOSE).show();
            } else {
                loadDataToTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //new Alert(Alert.AlertType.NONE,  hufSpinner.getValue() + "", ButtonType.CLOSE).show();
    }

    @FXML
    private void menuTableClicked() {
        descriptionTxt.setText(menuTable.getSelectionModel().getSelectedItem().getDesc());
    }

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadDataToTable();
    }

    private void loadDataToTable() {
        try {
            db = new DB();
            etelList = db.getEtlap();
            menuTable.getItems().clear();
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