package com.pixelbattle.extender.util;

import com.pixelbattle.extender.logic.FileManager;

public class Config {
    public static Config instance = FileManager.loadConfig("config.json");
    public String fillColor = "#ffffff";
    public int initialCanvasWidth = 0;
    public int initialCanvasHeight = 0;
    public int outputCanvasWidth = 1_000;
    public int outputCanvasHeight = 1_000;
    public int positioning = 0;
    public String canvasFileName = "empty.json";
    public String saveTo = "results";
    public int chunkLength = 1_000_000;
}