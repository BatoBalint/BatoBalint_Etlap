package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AddFoodController {
    @FXML
    private Spinner<Integer> priceInput;
    @FXML
    private ComboBox<String> categoryInput;
    @FXML
    private TextField nameInput;
    @FXML
    private Button addBtn;
    @FXML
    private TextArea descInput;

    public void initialize() {
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999));

        try {
            List<String> kategoriak = new DB().getKategoria();
            for (String k: kategoriak) {
                categoryInput.getItems().add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addBtnClick() {
        boolean ok = checkInput();
        if (ok) {
            try {
                DB db = new DB();
                String name = nameInput.getText();
                String category = categoryInput.getSelectionModel().getSelectedItem();
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
        if (categoryInput.getSelectionModel().getSelectedIndex() == -1) {
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

    public void menuItemClick(ActionEvent actionEvent) {

    }
}
