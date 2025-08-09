package com.pixelbattle.extender.wrappers;

import com.pixelbattle.extender.managers.DeserializeManager;
import com.pixelbattle.extender.primitives.Chunk;
import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.primitives.Position;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.RuntimeProperties;

import java.io.BufferedReader;
import java.io.IOException;

public class ChunkReadWrapper {
    public Chunk chunk;
    public DeserializeManager manager;


    public int readBatchIndex;

    public ChunkReadWrapper(BufferedReader stream) throws IOException {
        this.chunk = new Chunk(RuntimeProperties.CHUNK_LENGTH);
        this.manager = new DeserializeManager(stream);
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public boolean inBounds(Position pos, Position offset, Size size) {
        int relX = pos.x - offset.x;
        int relY = pos.y - offset.y;
        return relX >= 0 && relY >= 0 && relX < size.width && relY < size.height;
    }

    public void readBatch() throws IOException {
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public boolean next() throws IOException {
        if (!this.chunk.next()) {
            this.readBatch();
            this.chunk.toStart();
        }
        return this.chunk.index <= this.readBatchIndex;
    }

    public boolean isNotEnd() {
        return this.chunk.index < this.readBatchIndex;
    }

    public Pixel getPixel() {
        return this.chunk.getCurrentPixel();
    }
}
