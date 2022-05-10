package util;

import javafx.scene.control.Alert;

public class ReAlert {
    private Alert alert;

    public void setAlert(Alert.AlertType alertType, String s){
        alert = new Alert(alertType);
        alert.setContentText(s);
    }

    public void showAlert(){
        alert.showAndWait();
    }
}
