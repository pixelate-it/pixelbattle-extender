package com.pixelbattle.extender;

import com.google.gson.JsonParseException;
import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.logic.CanvasExtender;
import com.pixelbattle.extender.logic.CanvasVerifier;
import com.pixelbattle.extender.logic.FileManager;
import com.pixelbattle.extender.logic.TagLeaders;
import com.pixelbattle.extender.objects.Canvas;
import com.pixelbattle.extender.util.RuntimeProperties;
import com.pixelbattle.extender.util.ExtendingError;
import com.pixelbattle.extender.util.Transform;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class General {
    public static int countOfChunks = getCountOfChunks();

    public static long getWeightOfOutputCanvas() {
        int maxConsumingOfOnePixel = ("{\"x\":"+(RuntimeProperties.outputCanvasWidth-1)+",\"y\":"+(RuntimeProperties.outputCanvasHeight-1)+",\"author\":null,\"tag\":null,\"color\":\"#ffffff\"}").length();
        try {
            return ((long) RuntimeProperties.outputCanvasWidth * RuntimeProperties.outputCanvasHeight * maxConsumingOfOnePixel) - Files.size(Paths.get(RuntimeProperties.canvasFileName));
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static void updateNumberOfChunks() {
        countOfChunks = getCountOfChunks();
    }

    public static int getCountOfChunks() {
        return (int) Math.ceil((double) (RuntimeProperties.outputCanvasWidth * RuntimeProperties.outputCanvasHeight) / RuntimeProperties.chunkLength);
    }

    public static void runSelectCanvas() {
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("."));
        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = j.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            RuntimeProperties.canvasFileName = j.getSelectedFile().getPath();
        } else {
            RuntimeProperties.canvasFileName = "";
        }
    }

    public static void normalProcess(ProcessEventBus processBus) {
        try {
            long startTime = System.currentTimeMillis();
            Canvas canvas;
            if (RuntimeProperties.canvasEmpty)
                canvas = new Canvas(0,0);
            else canvas = FileManager.loadCanvas(RuntimeProperties.canvasFileName, processBus);

            Transform transform = Transform.fromIntToTransform(RuntimeProperties.positioning, canvas.width, canvas.height);
            if (transform != null) canvas.applyTransform(transform);

            String canvasOutputName = RuntimeProperties.generateCanvasFileName(RuntimeProperties.canvasEmpty ? "empty.json" : new File(RuntimeProperties.canvasFileName).getName());
            String tagsOutputName = RuntimeProperties.generateTagsFileName(canvasOutputName);

            File file = Paths.get(RuntimeProperties.saveTo, canvasOutputName).toFile();

            FileManager.configure(file);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                CanvasExtender.process(canvas, writer, processBus);
            } catch (IOException | ExtendingError | JsonParseException e) {
                System.out.println(e.getMessage());
                if (e instanceof IOException)
                    processBus.setExtendingStatus("IO Error");
                if (e instanceof JsonParseException)
                    processBus.setExtendingStatus("P Error");
                if (e instanceof ExtendingError)
                    processBus.setExtendingStatus("L Error");
            }

            CanvasVerifier.verifyCanvasFile(file, processBus);

            if (RuntimeProperties.generateTagTable && !RuntimeProperties.canvasEmpty) {
                File tagsFile = Paths.get(RuntimeProperties.saveTo, tagsOutputName).toFile();

                new FileWriter(tagsFile).write(TagLeaders.listTagLeaders(canvas.getPixels()));
            }

            long endTime = System.currentTimeMillis();
            long diff = endTime - startTime;
            processBus.setTotalStatus("Done in " + Math.floor((double) diff /100)/10 + "s.");
        } catch (IOException | JsonParseException e) {
            System.out.println(e.getMessage());
            MessageLauncher.launch(e.getMessage());
            if (e instanceof IOException)
                processBus.setParsingStatus("IO Error");
            else
                processBus.setParsingStatus("P Error");
        }
    }
}
