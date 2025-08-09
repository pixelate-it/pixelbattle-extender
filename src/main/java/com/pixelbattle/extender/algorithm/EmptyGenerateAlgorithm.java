package com.pixelbattle.extender.algorithm;

import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.RuntimeProperties;
import com.pixelbattle.extender.wrappers.ChunkWriteWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public class EmptyGenerateAlgorithm {
    public BufferedWriter outputStream;
    public ChunkWriteWrapper writeWrapper;

    public EmptyGenerateAlgorithm(BufferedWriter outputStream) throws IOException {
        this.outputStream = outputStream;
        this.writeWrapper = new ChunkWriteWrapper(outputStream);
    }

    public void run() throws IOException {
        Size extendSize = RuntimeProperties.OLD_SIZE;

        for (int y = 0; y < extendSize.height; y++) {
            for (int x = 0; x < extendSize.width; x++) {
                Pixel emptyPixel = new Pixel(x + RuntimeProperties.OLD_SIZE.width * y, null, null, RuntimeProperties.BASIC_FILL_COLOR);
                this.writeWrapper.add(emptyPixel);
            }
        }

        this.writeWrapper.endFlush();
    }
}
