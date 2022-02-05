package com.example.etlap.Controllers;

import com.example.etlap.DB;
import com.example.etlap.Etel;
import com.example.etlap.EtlapApp;
import com.example.etlap.Kategoria;
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
    private Button newInstanceBtn;
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
    @FXML
    private TabPane tabPane;
    @FXML
    private TableColumn<Kategoria, String> catTabCatCol;
    @FXML
    private TableView<Kategoria> catMenuTable;
    private DB db;
    private List<Etel> etelList;
    private List<Kategoria> katList;
    @FXML
    private ChoiceBox<Kategoria> catChoiceBox;

    @FXML
    public void newInstanceBtnClick() {
        String fxml = "";
        String title = "";
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                fxml = "add-food-view.fxml";
                title = "Étel felvétel";
                break;
            case 1:
                fxml = "add-category-view.fxml";
                title = "Kategória felvétel";
                break;
            default:
                break;
        }
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApp.class.getResource(fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setOnCloseRequest(windowEvent -> {
                switch (tabPane.getSelectionModel().getSelectedIndex()) {
                    case 0: loadDataToMenuTable();
                    break;
                    case 1: loadDataToCategoryTable();
                    break;
                }
            });
            stage.setOnHiding(windowEvent -> {
                switch (tabPane.getSelectionModel().getSelectedIndex()) {
                    case 0: loadDataToMenuTable();
                        break;
                    case 1: loadDataToCategoryTable();
                        break;
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBtnClick() {
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                foodDelete();
                break;
            case 1:
                categoryDelete();
                break;
        }
    }

    private void foodDelete() {
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
                    loadDataToMenuTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void categoryDelete() {
        int selectedIndex = catMenuTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            new Alert(Alert.AlertType.NONE, "Elöbb válasz ki egy kategóriát", ButtonType.CLOSE).show();
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Biztos hogy törölni szeretné a kategóriát? (" +
                            catMenuTable.getSelectionModel().getSelectedItem().getNev() +
                            ")");
            confirm.setTitle("Kategória törlése");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    db.deleteCategory(catMenuTable.getSelectionModel().getSelectedItem().getId());
                    loadDataToCategoryTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void percentageBtnClick() {
        try {
            boolean ok = false;
            if (menuTable.getSelectionModel().getSelectedIndex() == -1) {
                Optional<ButtonType> answer = new Alert(Alert.AlertType.CONFIRMATION,
                        "Jelenleg az összes sort szerkeszti, biztos benne?",
                        ButtonType.OK, ButtonType.CANCEL).showAndWait();
                if (answer.get() != ButtonType.OK) {
                    return;
                }
                ok = db.percentageChange(percentageSpinner.getValue());
            } else {
                ok = db.percentageChange(percentageSpinner.getValue(), menuTable.getSelectionModel().getSelectedItem().getId());
            }
            if (!ok) {
                new Alert(Alert.AlertType.ERROR, "Valami okbol fogva nem sikerült frissiteni az adato(ka)t", ButtonType.CLOSE).show();
            } else {
                loadDataToMenuTable();
                windowClicked();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        percentageSpinner.getValueFactory().setValue(0);
    }

    @FXML
    private void hufBtnClick() {
        try {
            boolean ok = false;
            if (menuTable.getSelectionModel().getSelectedIndex() == -1) {
                Optional<ButtonType> answer = new Alert(Alert.AlertType.CONFIRMATION,
                        "Jelenleg az összes sort szerkeszti, biztos benne?",
                        ButtonType.OK, ButtonType.CANCEL).showAndWait();
                if (answer.get() != ButtonType.OK) {
                    return;
                }
                ok = db.hufChange(hufSpinner.getValue());
            } else {
                ok = db.hufChange(hufSpinner.getValue(), menuTable.getSelectionModel().getSelectedItem().getId());
            }
            if (!ok) {
                new Alert(Alert.AlertType.ERROR, "Valami okbol fogva nem sikerült frissiteni az adatokat", ButtonType.CLOSE).show();
            } else {
                loadDataToMenuTable();
                windowClicked();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hufSpinner.getValueFactory().setValue(0);
    }

    @FXML
    private void menuTableClicked() {
        if (menuTable.getSelectionModel().getSelectedIndex() != -1) {
            descriptionTxt.setText(menuTable.getSelectionModel().getSelectedItem().getDesc());
        }
    }

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        catTabCatCol.setCellValueFactory(new PropertyValueFactory<>("nev"));

        loadDataToMenuTable();
        loadDataToCategoryTable();

        catChoiceBox.setOnAction(actionEvent -> {
            if (catChoiceBox.getSelectionModel().getSelectedItem().getId() == -1) {
                loadDataToMenuTable();
            } else {
                menuTable.getItems().clear();
                for (Etel e: etelList) {
                    if (e.getCategory().equals(catChoiceBox.getSelectionModel().getSelectedItem().getNev())) {
                        menuTable.getItems().add(e);
                    }
                }
            }
        });
    }

    private void loadDataToMenuTable() {
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

    private void loadDataToCategoryTable() {
        try {
            db = new DB();
            katList = db.getKategoria();
            catMenuTable.getItems().clear();
            catChoiceBox.getItems().clear();
            catChoiceBox.getItems().add(new Kategoria(-1, ""));
            for (Kategoria k: katList) {
                catMenuTable.getItems().add(k);
                catChoiceBox.getItems().add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void windowClicked() {
        menuTable.getSelectionModel().clearSelection();
        catMenuTable.getSelectionModel().clearSelection();
        descriptionTxt.setText("");
    }

    @FXML
    private void tabPaneClicked() {
        int tab = tabPane.getSelectionModel().getSelectedIndex();
        switch (tab) {
            case 0:
                newInstanceBtn.setText("Új étel felvétele");
                loadDataToMenuTable();
                break;
            case 1:
                newInstanceBtn.setText("Új kategória felvétele");
                loadDataToCategoryTable();
                break;
            default:
                break;
        }
    }

    private void test(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.CLOSE).show();
    }
}