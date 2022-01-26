package com.example.etlap;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddFoodController {
    @FXML
    private Spinner<Integer> priceInput;
    @FXML
    private TextField categoryInput;
    @FXML
    private TextField nameInput;
    @FXML
    private Button addBtn;
    @FXML
    private TextField descInput;

    public void initialize() {
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999));
    }

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
                int affectedRows = db.addEtel(name, category, desc, price);
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
