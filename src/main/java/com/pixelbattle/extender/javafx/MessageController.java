package com.pixelbattle.extender.javafx;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageController {
    @FXML
    public Text messageText;

    private final String message;

    public MessageController(String message) {
        this.message = message;
    }

    @FXML
    public void onOk() {
        Stage stage = (Stage) messageText.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        this.messageText.setText(this.message);
    }
}
