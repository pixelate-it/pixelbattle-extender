package com.pixelbattle.extender.util;

import com.pixelbattle.extender.logic.FileManager;
import com.pixelbattle.extender.objects.Canvas;

import java.util.Date;

public class RuntimeProperties {
    public static String fillColor = "#ffffff";
    public static int outputCanvasWidth = 1_000;
    public static int outputCanvasHeight = 1_000;
    public static int positioning = 0;
    public static String canvasFileName = "";
    public static int chunkLength = 1_000_000;
    public static boolean colorsAsNumber = false;
    public static boolean generateTagTable = false;
    public static boolean canvasEmpty = false;
    public static String saveTo = "results";
    public static String generateCanvasFileName(String canvasFileName) {
        String[] elements = canvasFileName.split("\\.");
        StringBuilder output = new StringBuilder();
        // Shit solution.
        for (int i = 0; i < elements.length-1; i++) {
            String part = elements[i];
            if (!part.matches("\\d+") || i != elements.length - 2) {
                output.append(part).append(".");
            }
        }
        output.append(new Date().getTime());
        output.append(".");
        return output + elements[elements.length-1];
    }
    public static String generateTagsFileName(String canvasFileName) {
        String name = canvasFileName.substring(0, canvasFileName.length()-5);
        return name + "tags.json";
    }
}