package com.pixelbattle.extender.launchers;

import com.pixelbattle.extender.GeneralProcesses;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainLauncher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/Montserrat.ttf")).toExternalForm(), 10);
        stage.setTitle("PixelBattle Extender");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        GeneralProcesses.createPaths();
        launch();
    }
}
