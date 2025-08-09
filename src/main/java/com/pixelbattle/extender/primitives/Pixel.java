package com.pixelbattle.extender.primitives;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pixelbattle.extender.runtime.RuntimeProperties;
import org.jetbrains.annotations.Nullable;


public class Pixel {
    public int _id;
    public Link author;
    public Link tag;

    public Color color;

    public Pixel() {
    }

    public Pixel(int _id, @Nullable Link tag, @Nullable Link author, Color color) {
        this._id = _id;
        this.tag = tag;
        this.author = author;
        this.color = color;
    }

    public Position getPosition() {
        double y = Math.floor((double) _id / RuntimeProperties.OLD_SIZE.width);
        double x = Math.floor(_id - y * RuntimeProperties.OLD_SIZE.width);
        return new Position((int) x, (int) y);
    }
}