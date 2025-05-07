package com.pixelbattle.extender.javafx;

import com.pixelbattle.extender.General;
import com.pixelbattle.extender.MessageLauncher;
import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.util.RuntimeProperties;
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
    public Button selectBtn;
    @FXML
    public Button outPreviewBtn;
    @FXML
    public Button inputPreviewBtn;
    @FXML
    public CheckBox colorsAsNumber;
    @FXML
    public CheckBox generateTagTable;

    public ProcessEventBus processBus = new ProcessEventBus();

    public Future<?> future;

    @FXML
    public void onStart() {
        try {
            this.clear();
            this.syncRuntimeProperties();
            startBtn.setDisable(true);

            future = CompletableFuture.runAsync(() -> {
                try {
                    General.normalProcess(processBus);
                } catch (Exception e) {
                    startBtn.setDisable(false);
                    outPreviewBtn.setDisable(true);
                    processBus.setParsingStatus("I Error");
                    System.out.println(e.getMessage());
                }
                startBtn.setDisable(false);
                outPreviewBtn.setDisable(false);
            });
        } catch (NumberFormatException e) {
            if (e instanceof NumberFormatException)
                MessageLauncher.launch("Config error: Int fields must be int values.");
            startBtn.setDisable(false);
        }
    }

    @FXML
    public void openFolder() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            File folder = new File(RuntimeProperties.saveTo);
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
    public void openSelectCanvas() {
        selectBtn.setDisable(true);
        CompletableFuture.runAsync(() -> {
            General.runSelectCanvas();
            if (!RuntimeProperties.canvasFileName.isEmpty()) {
                inputPreviewBtn.setDisable(false);
                startBtn.setDisable(false);
                RuntimeProperties.canvasEmpty = false;
                canvasStatus.setText("Canvas " +  RuntimeProperties.canvasFileName + " used.");
            }
            selectBtn.setDisable(false);
        });
    }

    @FXML
    public void openInputPreview() {

    }

    @FXML
    public void openOutPreview() {

    }

    @FXML
    public void useEmptyCanvas() {
        canvasStatus.setText("Used empty canvas");
        RuntimeProperties.canvasEmpty = true;
        inputPreviewBtn.setDisable(false);
        startBtn.setDisable(false);
    }

    public void initialize(){
        processBus.setProgressBar(progressBar);
        processBus.setProgressText(progressText);
        processBus.setIntegrityStatus(integrityStatus);
        processBus.setParsingStatus(parsingStatus);
        processBus.setExtendingStatus(extendingStatus);
        processBus.setTotalStatus(totalStatus);

        inputPreviewBtn.setDisable(true);
        outPreviewBtn.setDisable(true);
        startBtn.setDisable(true);

        canvasPosition.getItems().clear();
        for (TransformType t : TransformType.values()) {
            MenuItem item = new MenuItem(t.name);
            item.setOnAction((ActionEvent actionEvent) -> {
                RuntimeProperties.positioning = t.id;
                canvasPosition.setText(t.name);
            });
            canvasPosition.getItems().add(item);
        }
    }

    public void clear() {
        processBus.clear();

    }

    private void syncRuntimeProperties() {
        RuntimeProperties.outputCanvasWidth = Integer.parseInt(outputCanvasX.getText());
        RuntimeProperties.outputCanvasHeight = Integer.parseInt(outputCanvasY.getText());
        RuntimeProperties.chunkLength = Integer.parseInt(chunkLength.getText());
        RuntimeProperties.fillColor = fillColor.getText();
        RuntimeProperties.colorsAsNumber = colorsAsNumber.isSelected();
        RuntimeProperties.generateTagTable = generateTagTable.isSelected();
    }
}
