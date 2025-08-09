package com.pixelbattle.extender;

import com.pixelbattle.extender.algorithm.*;
import com.pixelbattle.extender.runtime.RuntimeProperties;
import com.pixelbattle.extender.runtime.Type;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Date;

public class GeneralProcesses {
    public static String tags(String filePath, Type.Tags tagType) throws IOException, Errors.ChunkAddPixelError {
        {
            File inputFile = Paths.get(filePath).toFile();
            String outputFileName = generateTagsFileName(inputFile.getName(), tagType);
            File outputFile = Paths.get(outputFileName).toFile();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            TagLeadersAlgorithm algorithm = new TagLeadersAlgorithm(reader, writer);
            algorithm.run(tagType);
            reader.close();
            writer.close();

            return outputFile.getName();
        }
    }

    public static String empty() throws IOException {
       {
            String resultPath = generateCanvasFileName("empty.json");
            File outputFile = Paths.get(resultPath).toFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            EmptyGenerateAlgorithm algorithm = new EmptyGenerateAlgorithm(writer);
            algorithm.run();

            return outputFile.getName();
       }
    }

    public static String extend(String filePathToCanvas) throws IOException, Errors.ChunkAddPixelError {
        {
            createPaths();
            File inputFile = Paths.get(filePathToCanvas).toFile();

            String resultPath = generateCanvasFileName(inputFile.getName());

            File outputFile = Paths.get(resultPath).toFile();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            BasicExtendAlgorithm algorithm = new BasicExtendAlgorithm(reader, writer);
            algorithm.run();

            reader.close();
            writer.close();

            return outputFile.getName();
        }
    }

    public static String image(String filePathToCanvas, Type.Image image) throws IOException {
        {
            File inputFile, outputFile;
            inputFile = Paths.get(filePathToCanvas).toFile();
            outputFile = Paths.get(generatePngFileName(inputFile.getName(), image)).toFile();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            OutputStream writer = new FileOutputStream(outputFile);

            reader.close();

            reader = new BufferedReader(new FileReader(inputFile));

            ImageGenerateAlgorithm algorithm = new ImageGenerateAlgorithm(reader, writer, RuntimeProperties.OLD_SIZE);
            algorithm.run(image);

            writer.close();

            return outputFile.getName();
        }
    }

    public static void createPaths() {
        Paths.get(RuntimeProperties.RESULT_PATH).toFile().mkdir();
        Paths.get(RuntimeProperties.EXTENDED_PATH).toFile().mkdir();
        Paths.get(RuntimeProperties.IMAGES_PATH).toFile().mkdir();
        Paths.get(RuntimeProperties.TAGS_PATH).toFile().mkdir();
    }

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
        output.append(".json");
        return Paths.get("results", "canvas", output.toString()).toString();
    }

    public static String generateTagsFileName(String canvasFileName, Type.Tags tags) {
        String name = canvasFileName.substring(0, canvasFileName.length()-5);
        return Paths.get("results", "tags", name + ".tags" + tags.suffix).toString();
    }

    public static String generatePngFileName(String canvasFileName, Type.Image image) {
        String name = canvasFileName.substring(0, canvasFileName.length()-5);
        return Paths.get("results", "images", name + image.suffix).toString();
    }

    public static String runSelectCanvas() {
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("."));
        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = j.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return j.getSelectedFile().getPath();
        } else {
            return null;
        }
    }
}
