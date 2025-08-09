package com.pixelbattle.extender.managers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixelbattle.extender.primitives.Chunk;
import com.pixelbattle.extender.primitives.Pixel;

import java.io.BufferedReader;
import java.io.IOException;

public class DeserializeManager {
    public BufferedReader stream;
    public JsonFactory factory;
    public JsonParser parser;
    public ObjectMapper mapper;

    public DeserializeManager(BufferedReader stream) throws IOException {
        this.stream = stream;
        this.factory = new JsonFactory();
        this.parser = this.factory.createParser(stream);
        this.parser.nextToken(); // Skip array start
        this.mapper = new ObjectMapper();
    }

    public int deserializeBatch(Chunk chunk) throws IOException {
        chunk.toStart();
        int index = 0;

        if (parser.isClosed()) return -1;

        while (!parser.isClosed() && index < chunk.length) {
            JsonToken token = parser.nextToken();

            if (JsonToken.START_OBJECT.equals(token)) {
                Pixel pixel = mapper.readValue(parser, Pixel.class);
                chunk.setPixel(index, pixel);
                chunk.next();
                index++;
            }
        }
        chunk.toStart();

        return index;
    }
}
