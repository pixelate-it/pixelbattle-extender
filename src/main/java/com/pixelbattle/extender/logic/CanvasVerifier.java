package com.pixelbattle.extender.logic;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.pixelbattle.extender.events.ProcessEventBus;

import java.io.File;
import java.io.FileInputStream;

public class CanvasVerifier {
    public static void verifyCanvasFile(File canvasFile, ProcessEventBus event) {
        event.setIntegrityStatus("Started");

        long startTime = System.currentTimeMillis();

        JsonFactory factory = new JsonFactory();
        try (FileInputStream fis = new FileInputStream(canvasFile);
             JsonParser parser = factory.createParser(fis)) {

            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();

                if (JsonToken.START_OBJECT.equals(token)) {
                    if (!verifyPixel(parser)) {
                        event.setIntegrityStatus("Error");
                        return;
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long diff = endTime - startTime;
            event.setIntegrityStatus("Done in " + Math.floor((double) diff /1000) + "s.");
        } catch (Exception  e) {
            event.setIntegrityStatus("P Error");
        }
    }

    private static boolean verifyPixel(JsonParser parser) throws Exception {
        int x = -1, y = -1;
        String author = null, tag = null, color = null;

        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldName = parser.getCurrentName();
                token = parser.nextToken();

                switch (fieldName) {
                    case "x":
                        if (!JsonToken.VALUE_NUMBER_INT.equals(token)) return false;
                        break;
                    case "y":
                        if (!JsonToken.VALUE_NUMBER_INT.equals(token)) return false;
                        y = parser.getIntValue();
                        break;
                    case "author":
                        if (!JsonToken.VALUE_NULL.equals(token) && !JsonToken.VALUE_STRING.equals(token)) return false;
                        author = parser.getText();
                        break;
                    case "tag":
                        if (!JsonToken.VALUE_NULL.equals(token) && !JsonToken.VALUE_STRING.equals(token)) return false;
                        tag = parser.getText();
                        break;
                    case "color":
                        if (!JsonToken.VALUE_STRING.equals(token)) return false;
                        color = parser.getText();
                        if (!isValidColor(color)) return false;
                        break;
                }
            } else if (JsonToken.END_OBJECT.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidColor(String color) {
        return color != null && color.matches("^#[0-9a-fA-F]{6}$");
    }
}
