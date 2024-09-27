package com.pixelbattle.extender.javafx;

import com.pixelbattle.extender.General;
import com.pixelbattle.extender.MessageLauncher;
import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.logic.CanvasVerifier;
import com.pixelbattle.extender.util.Config;
import com.pixelbattle.extender.util.ExceptionToShow;
import com.pixelbattle.extender.util.TransformType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class MainController {
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Text progressText;
    @FXML
    public TextField initialCanvasX;
    @FXML
    public TextField initialCanvasY;
    @FXML
    public TextField outputCanvasX;
    @FXML
    public TextField outputCanvasY;
    @FXML
    public TextField chunkLength;
    @FXML
    public MenuButton canvasPosition;
    @FXML
    public TextField fillColor;
    @FXML
    public Text parsingStatus;
    @FXML
    public Text extendingStatus;
    @FXML
    public Text integrityStatus;
    @FXML
    public Text totalStatus;
    @FXML
    public Text canvasStatus;
    @FXML
    public Button startBtn;
    @FXML
    public Button stopBtn;
    @FXML
    public Button selectBtn;

    public ProcessEventBus processBus = new ProcessEventBus();

    public Future<?> future;

    @FXML
    public void onStart() {
        try {
            this.clear();
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
            loadToConfig();

            future = CompletableFuture.runAsync(() -> {
                General.normalProcess(processBus);
                startBtn.setDisable(false);
                stopBtn.setDisable(true);
            });
        } catch (NumberFormatException | ExceptionToShow e) {
            if (e instanceof NumberFormatException)
                MessageLauncher.launch("Config error: Int fields must be int values.");
            if (e instanceof ExceptionToShow)
                MessageLauncher.launch(((ExceptionToShow) e).getMessage());
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
        }
    }

    @FXML
    public void onStop() {
//        if (future != null && !(future.isCancelled() || future.isDone())) {
//            future.cancel(true);
//            startBtn.setDisable(false);
//            stopBtn.setDisable(true);
//        }
    }

    @FXML
    public void onOpenFolder() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            File folder = new File(Config.instance.saveTo);
            try {
                Desktop.getDesktop().open(folder);
            } catch (Exception e) {
                MessageLauncher.launch("Error with opening explorer utility, for display folder");
            }
        } else {
            MessageLauncher.launch("Your os is not support this thing");
        }
    }

    @FXML
    public void onLoad() {
        General.loadConfig();
        this.processConfig();
    }

    @FXML
    public void onSave() {
        try {
            loadToConfig();
            General.saveConfig();
            MessageLauncher.launch("Config saved successfully!");
        } catch (NumberFormatException | ExceptionToShow e) {
            if (e instanceof NumberFormatException)
                MessageLauncher.launch("Int fields must be int values.");
            else
                MessageLauncher.launch(((ExceptionToShow) e).getMessage());
        }
    }

    @FXML
    public void onSelectCanvas() {
        selectBtn.setDisable(true);
        CompletableFuture.runAsync(() -> {
            General.runSelectCanvas();
            this.processConfig();
            General.saveConfig();
            selectBtn.setDisable(false);
        });
    }

    public void initialize(){
        processBus.setProgressBar(progressBar);
        processBus.setProgressText(progressText);
        processBus.setIntegrityStatus(integrityStatus);
        processBus.setParsingStatus(parsingStatus);
        processBus.setExtendingStatus(extendingStatus);
        processBus.setTotalStatus(totalStatus);
        processConfig();

        canvasPosition.getItems().clear();
        for (TransformType t : TransformType.values()) {
            MenuItem item = new MenuItem(t.name);
            item.setOnAction((ActionEvent actionEvent) -> {
                Config.instance.positioning = t.id;
                canvasPosition.setText(t.name);
            });
            canvasPosition.getItems().add(item);
        }
    }

    public void loadToConfig() throws NumberFormatException, ExceptionToShow {
        if (!CanvasVerifier.isValidColor(fillColor.getText())) {
            throw new ExceptionToShow("Fill color must be HEX with 6 digits");
        }
        Config.instance.initialCanvasWidth = Integer.parseInt(initialCanvasX.getText());
        Config.instance.initialCanvasHeight = Integer.parseInt(initialCanvasY.getText());
        Config.instance.outputCanvasWidth = Integer.parseInt(outputCanvasX.getText());
        Config.instance.outputCanvasHeight = Integer.parseInt(outputCanvasY.getText());
        Config.instance.chunkLength = Integer.parseInt(chunkLength.getText());
        Config.instance.fillColor = fillColor.getText();
        General.updateNumberOfChunks();
    }

    public void clear() {
        processBus.clear();
    }

    public void processConfig() {
        long size = General.getWeightOfOutputCanvas();

        initialCanvasX.setText(""+Config.instance.initialCanvasWidth);
        initialCanvasY.setText(""+Config.instance.initialCanvasHeight);
        outputCanvasX.setText(""+Config.instance.outputCanvasWidth);
        outputCanvasY.setText(""+Config.instance.outputCanvasHeight);
        chunkLength.setText(""+Config.instance.chunkLength);
        fillColor.setText(Config.instance.fillColor);
        if (size == 0)
            canvasStatus.setText("Canvas file is not loaded");
        else
            canvasStatus.setText("Canvas file is loaded "+ new File(Config.instance.canvasFileName).getName());

        for (TransformType t : TransformType.values()) {
            if (t.id == Config.instance.positioning)
                canvasPosition.setText(t.name);
        }
    }
}
