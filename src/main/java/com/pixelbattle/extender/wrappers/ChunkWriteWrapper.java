package com.pixelbattle.extender.wrappers;

import com.pixelbattle.extender.managers.SerializeManager;
import com.pixelbattle.extender.primitives.Chunk;
import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.runtime.RuntimeProperties;

import java.io.BufferedWriter;
import java.io.IOException;

public class ChunkWriteWrapper {
    public Chunk chunk;
    public BufferedWriter stream;
    public SerializeManager manager;
    private boolean first;

    public ChunkWriteWrapper(BufferedWriter stream) throws IOException {
        this.chunk = new Chunk(RuntimeProperties.CHUNK_LENGTH);
        this.stream = stream;
        this.manager = new SerializeManager(stream);
        this.first = true;
    }

    public void endFlush() throws IOException {
        if (this.chunk.index > 0) {
            if (!this.first) this.manager.mark();
            this.manager.flush(this.chunk);
        }
        this.manager.end();
    }

    public void add(Pixel pixel) throws IOException {
        this.chunk.setPixel(this.chunk.index, pixel);
        if (!this.chunk.next()) {
            if (!this.first) this.manager.mark();
            this.first = false;
            this.manager.flush(this.chunk);
            chunk.toStart();
        }
    }
}
