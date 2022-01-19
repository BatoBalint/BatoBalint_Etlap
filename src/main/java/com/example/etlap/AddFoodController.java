package com.example.etlap;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.InputEvent;

import java.sql.SQLException;

public class AddFoodController {
    @FXML
    private Spinner priceInput;
    @FXML
    private TextField categoryInput;
    @FXML
    private TextField nameInput;
    @FXML
    private Button addBtn;
    @FXML
    private TextField descInput;

    @FXML
    public void addBtnClick() {
        boolean ok = checkInput();
        if (ok) {
            try {
                DB db = new DB();
                String name = nameInput.getText();
                String category = categoryInput.getText();
                String desc = descInput.getText();
                int price = Integer.parseInt(priceInput.getValue().toString());
                db.addEtel(name, category, desc, price);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void hiba(String hibak) {
        new Alert(Alert.AlertType.ERROR, hibak, ButtonType.CLOSE).show();
    }

    private boolean checkInput() {
        boolean error = false;
        String hibak = "";
        if (nameInput.getText().isEmpty()) {
            error = true;
            if (hibak.length() != 0) hibak += "\n";
            hibak += "A nev nem lehet ures";
        }
        if (categoryInput.getText().isEmpty()) {
            error = true;
            if (hibak.length() != 0) hibak += "\n";
            hibak += "A kategoria nem lehet ures";
        }
        if (descInput.getText().isEmpty()) {
            error = true;
            if (hibak.length() != 0) hibak += "\n";
            hibak += "A leiras nem lehet ures";
        }
        hiba(hibak);
        return !error;
    }
}
