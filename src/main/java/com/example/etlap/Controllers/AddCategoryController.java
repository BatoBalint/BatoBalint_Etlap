package com.example.etlap.Controllers;

import com.example.etlap.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCategoryController {

    @FXML
    private Button addBtn;
    @FXML
    private TextField categoryInput;
    private DB db;


    public void initialize() {
        try {
            db = new DB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addBtnClick() {
        try {
            if (db.hasKategoria(categoryInput.getText())) {
                new Alert(Alert.AlertType.NONE, "Már létezik kategória ilyen névvel", ButtonType.OK).show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean ok = checkInput();
        if (ok) {
            try {
                DB db = new DB();
                String category = categoryInput.getText();
                int affectedRows = db.addKategoria(category);
                if(affectedRows == 1) {
                    Stage stage = (Stage) addBtn.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void hiba(String hibak) {
        if (hibak.length() > 0) {
            new Alert(Alert.AlertType.ERROR, hibak, ButtonType.CLOSE).show();
        }
    }

    private boolean checkInput() {
        boolean error = false;
        String hibak = "";
        if (categoryInput.getText().isEmpty()) {
            error = true;
            if (hibak.length() != 0) hibak += "\n";
            hibak += "A nev nem lehet ures";
        }
        hiba(hibak);
        return !error;
    }
}
