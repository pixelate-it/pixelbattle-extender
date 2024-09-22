package com.pixelbattle.extender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PixelBattle Extender");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws InterruptedException {
//        General.service.shutdown();
//        General.service.awaitTermination(1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        launch();
    }
}