package com.pixelbattle.extender.managers;

import com.pixelbattle.extender.runtime.Type;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TagManager {
    public Map<String, Integer> tags;
    public BufferedWriter writer;

    public TagManager(Map<String, Integer> tags, BufferedWriter writer) {
        this.tags = tags;
        this.writer = writer;
    }

    public List<Map.Entry<String, Integer>> sort() {
        return tags.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .toList();
    }

    public String serializeTag(String tagName, Integer pixels) {
        return "\"" + tagName + "\" : " + pixels;
    }

    private final String[] emoji = {"\uD83E\uDD47", "\uD83E\uDD48", "\uD83E\uDD49"};

    public void write(Type.Tags tagType) throws IOException {
        switch (tagType) {
            case Type.Tags.JSON -> {
                this.writer.write("{\n");
                List<Map.Entry<String, Integer>> tags = this.sort();
                for (int i = 0; i < tags.size(); i++) {
                    Map.Entry<String, Integer> tag = tags.get(i);
                    this.writer.write("\t" + this.serializeTag(String.valueOf(tag.getKey()), tag.getValue()) + (i == tags.size() - 1 ? "\n" : ",\n"));
                }
                this.writer.write("}");
                this.writer.flush();
            }
            case Type.Tags.TABLE -> {
                List<Map.Entry<String, Integer>> tags = this.sort();
                for (int i = 0; i < tags.size(); i++) {
                    Map.Entry<String, Integer> tag = tags.get(i);
                    String name = String.valueOf(tag.getKey());
                    int pixels = tag.getValue();

                    if (i < emoji.length)
                        this.writer.write(emoji[i] + " ");
                    this.writer.write(name + " " + pixels + "\n");
                }
                this.writer.flush();
            }
        }

    }
}
