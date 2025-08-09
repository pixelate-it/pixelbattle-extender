package com.pixelbattle.extender.algorithm;

import com.pixelbattle.extender.Errors;
import com.pixelbattle.extender.managers.TagManager;
import com.pixelbattle.extender.primitives.Link;
import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.runtime.Type;
import com.pixelbattle.extender.wrappers.ChunkReadWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TagLeadersAlgorithm {
    public BufferedReader reader;
    public BufferedWriter writer;
    public ChunkReadWrapper wrapper;
    public TagManager manager;

    public TagLeadersAlgorithm(BufferedReader reader, BufferedWriter writer) throws IOException, Errors.ChunkAddPixelError {
        this.writer = writer;
        this.reader = reader;
        this.wrapper = new ChunkReadWrapper(reader);
    }

    public void run(Type.Tags tagType) throws IOException, Errors.ChunkAddPixelError {
        Map<String, Integer> tags = new HashMap<>();
        while (this.wrapper.isNotEnd()) {
            Pixel pixel = this.wrapper.getPixel();
            if (this.wrapper.next()) {
                Link tag = pixel.tag;
                if (tag != null)
                    tags.merge(tag.toString(), 1, Integer::sum);
            }
        }

        this.manager = new TagManager(tags, writer);

        this.manager.write(tagType);
    }
}
