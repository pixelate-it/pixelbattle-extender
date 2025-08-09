package com.pixelbattle.extender.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MessageController {
    @FXML
    TextArea Container;

    private final String message;

    public MessageController(String message) {
        this.message = message;
    }

    @FXML void onOk() {
        Stage stage = (Stage) Container.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        Container.setText(message);
    }
}
