package com.pixelbattle.extender.algorithm;

import com.pixelbattle.extender.Errors;
import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.primitives.Position;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.RuntimeProperties;
import com.pixelbattle.extender.wrappers.ChunkReadWrapper;
import com.pixelbattle.extender.wrappers.ChunkWriteWrapper;

import java.io.*;

public class BasicExtendAlgorithm {
    public BufferedReader inputStream;
    public BufferedWriter outputStream;
    public ChunkReadWrapper readWrapper;
    public ChunkWriteWrapper writeWrapper;

    public BasicExtendAlgorithm(BufferedReader inputStream, BufferedWriter outputStream) throws IOException, Errors.ChunkAddPixelError {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.readWrapper = new ChunkReadWrapper(inputStream);
        this.writeWrapper = new ChunkWriteWrapper(outputStream);

    }

    public void run() throws IOException, Errors.ChunkAddPixelError {
        Size extendSize = RuntimeProperties.EXTEND_SIZE;
        Size oldSize = RuntimeProperties.OLD_SIZE;

        Position oldCanvasPosition = RuntimeProperties.POSITION.toPosition(oldSize, extendSize);

        for (int y = 0; y < extendSize.height; y++) {
            for (int x = 0; x < extendSize.width; x++) {
                int newId = (y * extendSize.width) + x;
                Pixel emptyPixel = new Pixel(newId, null, null, RuntimeProperties.BASIC_FILL_COLOR);
                if (this.readWrapper.isNotEnd()) {
                    if (this.readWrapper.inBounds(new Position(x,y), oldCanvasPosition, oldSize)) {
                        Pixel oldPixel = this.readWrapper.getPixel();
                        this.writeWrapper.add(new Pixel(newId, oldPixel.tag, oldPixel.author, oldPixel.color));
                        this.readWrapper.next();
                    } else this.writeWrapper.add(emptyPixel);
                } else {
                    this.writeWrapper.add(emptyPixel);
                }
            }
        }

        this.writeWrapper.endFlush();
    }
}
