package com.pixelbattle.extender;

import com.pixelbattle.extender.javafx.MessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MessageLauncher {
    public static void launch(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MessageLauncher.class.getResource("/fxml/message.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            MessageController mc = new MessageController(message);
            fxmlLoader.setController(mc);
            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(Objects.requireNonNull(MessageLauncher.class.getResourceAsStream("/images/icon.png"))));
            stage.setTitle("PixelBattle Extender");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ignored) {

        }
    }
}
