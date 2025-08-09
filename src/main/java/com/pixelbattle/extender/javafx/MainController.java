package com.pixelbattle.extender.javafx;

import com.pixelbattle.extender.GeneralProcesses;
import com.pixelbattle.extender.launchers.MessageLauncher;
import com.pixelbattle.extender.primitives.Color;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.RuntimeProperties;
import com.pixelbattle.extender.runtime.Type;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class MainController {
    // Global

    @FXML TextField IWidth;
    @FXML TextField IHeight;
    @FXML Slider MemoryUsage;
    @FXML Button SelectCanvas;
    @FXML Button Start;
    @FXML Text Status;
    @FXML TabPane TabPane;

    Type.Page currentPage = Type.Page.EXTEND;

    private String filePath;

    @FXML void SelectCanvasClick() {
        Start.setDisable(true);
        SelectCanvas.setDisable(true);
        CompletableFuture.runAsync(() -> {
            filePath = GeneralProcesses.runSelectCanvas();
            if (filePath != null)
                Status.setText("Canvas " + Paths.get(filePath).getFileName() + " selected.");
            Start.setDisable(false);
            SelectCanvas.setDisable(false);
        });
    }

    @FXML void StartClick() {
        try {
            if (filePath == null && currentPage != Type.Page.EMPTY) return;

            RuntimeProperties.CHUNK_LENGTH = (int) (50000 * (MemoryUsage.getValue() / 102));
            RuntimeProperties.OLD_SIZE = new Size(Integer.parseInt(IWidth.getText()), Integer.parseInt(IHeight.getText()));
            RuntimeProperties.EXTEND_SIZE = new Size(Integer.parseInt(ExtendOWidth.getText()), Integer.parseInt(ExtendOHeight.getText()));
            RuntimeProperties.BASIC_FILL_COLOR = new Color(ExtendFillColor.getText());
            Status.setText("In process");
            SelectCanvas.setDisable(true);
            Start.setDisable(true);
            CompletableFuture.runAsync(() -> {
                try {
                    String a = "";
                    switch (currentPage) {
                        case Type.Page.EXTEND ->
                            a = GeneralProcesses.extend(filePath);
                        case Type.Page.TAGS ->
                            a = GeneralProcesses.tags(filePath, tagType);
                        case Type.Page.IMAGE ->
                            a = GeneralProcesses.image(filePath, imageType);
                        case Type.Page.EMPTY -> {
                            RuntimeProperties.BASIC_FILL_COLOR = new Color(EmptyCanvasFill.getText());
                            a = GeneralProcesses.empty();
                        }
                    }
                    Status.setText("Success! File " + a);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    Status.setText("Failed");
                    Platform.runLater(() -> MessageLauncher.run(e.toString()));
                }
                Start.setDisable(false);
                SelectCanvas.setDisable(false);
            });
        } catch (Exception e) {
            System.out.println(e.toString());
            MessageLauncher.run(e.toString());
        }
    }

    @FXML void SelectionChanged(Event event) {
        Tab tab = (Tab) event.getSource();
        if (tab != null) {
            for (Type.Page page : Type.Page.values()) {
                if (tab.getText().equals(page.tabName)) {
                    currentPage = page;
                    return;
                }
            }
        }
    }

    void initializeGlobal() {
    }

    // Global End

    // Extend tab

    @FXML TextField ExtendFillColor;
    @FXML MenuButton ExtendCanvasPosition;
    @FXML TextField ExtendOWidth;
    @FXML TextField ExtendOHeight;

    void initExtendTab() {
        ExtendCanvasPosition.getItems().clear();
        for (Type.CanvasPosition t : Type.CanvasPosition.values()) {
            MenuItem item = new MenuItem(t.name);
            item.setOnAction((ActionEvent) -> {
                RuntimeProperties.POSITION = t;
                ExtendCanvasPosition.setText(t.name);
            });
            ExtendCanvasPosition.getItems().add(item);
        }
    }

    // Extend tab end

    // Image tab

    @FXML MenuButton ImageType;

    private Type.Image imageType = Type.Image.PNG;

    void initImageTab() {
        ImageType.getItems().clear();
        for (Type.Image t : Type.Image.values()) {
            MenuItem item = new MenuItem(t.name);
            item.setOnAction((ActionEvent) -> {
                imageType = t;
                ImageType.setText(t.name);
            });
            ImageType.getItems().add(item);
        }
    }

    // Image tab end

    // Tags tab

    @FXML MenuButton TagType;

    private Type.Tags tagType = Type.Tags.JSON;

    void initTagsTab() {
        TagType.getItems().clear();
        for (Type.Tags t : Type.Tags.values()) {
            MenuItem item = new MenuItem(t.name);
            item.setOnAction((ActionEvent) -> {
                tagType = t;
                TagType.setText(t.name);
            });
            TagType.getItems().add(item);
        }
    }

    // Tags end

    // Empty Create

    @FXML TextField EmptyCanvasFill;

    // Empty end

    @FXML
    void initialize() {
        this.initExtendTab();
        this.initializeGlobal();
        this.initImageTab();
        this.initTagsTab();
    }
}
