package com.pixelbattle.extender.objects;

public class Pixel {
    public Long x;
    public Long y;
    public String author;
    public String tag;
    public String color;

    public Pixel(Long x, Long y, String author, String tag, String color) {
        this.x = x;
        this.y = y;
        this.tag = tag;
        this.color = color;
        this.author = author;
    }

    public String toString() {
        return "{\"x\":" + this.x + ",\"y\":" + this.y +
                ",\"author\":" + (this.author == null ? "null" : "\"" + this.author + "\"") +
                ",\"tag\":" + (this.tag == null ? "null" : "\"" + this.tag + "\"") +
                ",\"color\":" + (this.color == null ? "null" : "\"" + this.color + "\"") + "}";
    }
}
