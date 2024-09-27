package com.pixelbattle.extender.logic;

import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.objects.Canvas;
import com.pixelbattle.extender.objects.Chunk;
import com.pixelbattle.extender.objects.Pixel;
import com.pixelbattle.extender.util.Config;
import com.pixelbattle.extender.util.ExtendingError;

import java.io.BufferedWriter;
import java.io.IOException;

public class CanvasExtender {
    public static void process(Canvas initialCanvas, BufferedWriter writer, ProcessEventBus event) throws ExtendingError, IOException {
        int width = Config.instance.outputCanvasWidth;
        int height = Config.instance.outputCanvasHeight;

        long startTime = System.currentTimeMillis();

        event.setChunksCount(0);

        Chunk bufferChunk = new Chunk(Config.instance.chunkLength);

        event.setExtendingStatus("Started");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel;
                if (initialCanvas.isPixelInside(x, y)) {
                    Pixel oldPixel = initialCanvas.getPixel(x, y);
                    pixel = new Pixel((long) x, (long) y, oldPixel.author, oldPixel.tag, oldPixel.color);
                } else pixel = new Pixel((long) x, (long) y, null, null, Config.instance.fillColor);

                if (!bufferChunk.add(pixel)) {
                    FileManager.writeChunk(bufferChunk, writer, event.chunksCount);
                    event.nextStepOfExtending();
                    bufferChunk.clear();
                    bufferChunk.add(pixel);
                }
            }
        }

        if (!bufferChunk.isLast()) {
            FileManager.writeChunk(bufferChunk, writer, event.chunksCount);
            event.nextStepOfExtending();
        }

        writer.flush();

        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        event.setExtendingStatus("Done in " + Math.floor((double) diff / 1000) + "s.");
    }
}
