package com.pixelbattle.extender.launchers;

import com.pixelbattle.extender.javafx.MessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class MessageLauncher {
    public static void run(String message) {
        try {
            Font.loadFont(Objects.requireNonNull(MessageLauncher.class.getResource("/fonts/Montserrat.ttf")).toExternalForm(), 10);
            FXMLLoader fxmlLoader = new FXMLLoader(MessageLauncher.class.getResource("/fxml/message.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            MessageController mc = new MessageController(message);
            fxmlLoader.setController(mc);
            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image(Objects.requireNonNull(MessageLauncher.class.getResourceAsStream("/images/icon.png"))));
            stage.setTitle("Error message");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
