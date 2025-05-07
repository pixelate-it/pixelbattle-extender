package com.pixelbattle.extender.logic;

import com.google.gson.Gson;
import com.pixelbattle.extender.objects.Pixel;
import com.pixelbattle.extender.util.RuntimeProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagLeaders {
    public static String listTagLeaders(Pixel[] pixels) {
        Map<String, Integer> tags = new HashMap<>();
        for (Pixel pixel : pixels) {
            String tag = pixel.tag;
            if (RuntimeProperties.generateTagTable && tag != null) {
                tags.merge(tag, 1, Integer::sum);
            }
        }

        List<Map.Entry<String, Integer>> sorted = tags.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .toList();

        Gson g = new Gson();
        return g.toJson(sorted);
    }
}
