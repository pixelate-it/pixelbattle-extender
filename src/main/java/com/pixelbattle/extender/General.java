package com.pixelbattle.extender;

import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.logic.CanvasExtender;
import com.pixelbattle.extender.logic.CanvasVerifier;
import com.pixelbattle.extender.logic.FileManager;
import com.pixelbattle.extender.objects.Canvas;
import com.pixelbattle.extender.util.Config;
import com.pixelbattle.extender.util.ExtendingError;
import com.pixelbattle.extender.util.Transform;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class General {
    public static int processedChunksCount = 0;
    public static int countOfChunks = getCountOfChunks();
//    public static ExecutorService service = Executors.newFixedThreadPool(1);

    public static long getWeightOfOutputCanvas() {
        int maxConsumingOfOnePixel = ("{\"x\":"+(Config.instance.outputCanvasWidth-1)+",\"y\":"+(Config.instance.outputCanvasHeight-1)+",\"author\":null,\"tag\":null,\"color\":\"#ffffff\"}").length();
        try {
            return ((long) Config.instance.outputCanvasWidth * Config.instance.outputCanvasHeight * maxConsumingOfOnePixel) - Files.size(Paths.get(Config.instance.canvasFileName));
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static void updateNumberOfChunks() {
        countOfChunks = getCountOfChunks();
    }

    public static int getCountOfChunks() {
        return (int) Math.ceil((double) (Config.instance.outputCanvasWidth * Config.instance.outputCanvasHeight) / Config.instance.chunkLength);
    }

    public static void runSelectCanvas() {
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("."));
        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = j.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Config.instance.canvasFileName = j.getSelectedFile().toString();
        }
    }

    public static void normalProcess(ProcessEventBus processBus) {
        try {
            long startTime = System.currentTimeMillis();

            Canvas canvas = FileManager.loadCanvas(Config.instance.canvasFileName, Config.instance.initialCanvasWidth, Config.instance.initialCanvasHeight, processBus);

            Transform transform = Transform.fromIntToTransform(Config.instance.positioning);
            if (transform != null) canvas.applyTransform(transform);

            File file = Paths.get(Config.instance.saveTo, new File(Config.instance.canvasFileName).getName()).toFile();

            FileManager.configure(file);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                CanvasExtender.process(canvas, writer, processBus);
            } catch (IOException | ExtendingError e) {
                System.out.println(e.getMessage());
                if (e instanceof IOException)
                    processBus.setExtendingStatus("IO Error");
                if (e instanceof ExtendingError)
                    processBus.setExtendingStatus("L Error");
            }

            CanvasVerifier.verifyCanvasFile(file, processBus);

            long endTime = System.currentTimeMillis();
            long diff = endTime - startTime;
            processBus.setTotalStatus("Done in " + Math.floor((double) diff /100)/10 + "s.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            processBus.setParsingStatus("IO Error");
        }
    }

    public static void loadConfig() {
        Config.instance = FileManager.loadConfig("config.json");
    }

    public static void saveConfig() {
       FileManager.saveConfig(Config.instance,"config.json");
    }
}
