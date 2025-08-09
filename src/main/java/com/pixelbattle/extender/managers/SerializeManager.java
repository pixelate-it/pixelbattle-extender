package com.pixelbattle.extender.managers;

import com.pixelbattle.extender.primitives.Chunk;
import com.pixelbattle.extender.primitives.Color;
import com.pixelbattle.extender.primitives.Pixel;

import java.io.BufferedWriter;
import java.io.IOException;

public class SerializeManager {
    public BufferedWriter stream;

    public SerializeManager(BufferedWriter stream) throws IOException {
        this.stream = stream;
        this.stream.write("[");
        this.stream.flush();
    }

    public String serializePixel(Pixel pixel) {
        return "{\"_id\":" + pixel._id +
                ",\"author\":" + (pixel.author == null ? "null" : "{\"$numberLong\":\"" + pixel.author + "\"}") +
                ",\"tag\":" + (pixel.tag == null ? "null" : "{\"$numberLong\":\"" + pixel.tag + "\"}") +
                ",\"color\":" + (pixel.color == null ? "null" : serializeColor(pixel.color)) + "}";
    }

    public String serializeColor(Color color) {
        return "" + color.toInt();
    }

    public void flush(Chunk chunk) throws IOException {
        for (int index = 0; index < chunk.index; index++) {
            this.stream.write(serializePixel(chunk.pixels[index]));
            if (index + 1 < chunk.index)
                this.stream.write(",");
        }
        this.stream.flush();
    }

    public void mark() throws IOException {
        this.stream.write(",");
    }

    public void end() throws IOException {
        this.stream.write("]");
        this.stream.flush();
    }
}
