package com.pixelbattle.extender.objects;

public class Chunk {
    public int lastId = 0;
    Pixel[] pixels;
    int length = 0;

    public Chunk(int length) {
        this.pixels = new Pixel[length];
        this.length = length;
    }

    public Pixel get(int index) {
        return this.pixels[index];
    }

    public boolean add(Pixel pixel) {
        if (this.lastId < this.length) {
            this.pixels[this.lastId] = pixel;
            this.lastId++;
            return true;
        }
        return false;
    }

    public void clear() {
        this.lastId = 0;
    }

    public boolean isLast() {
        return this.lastId == this.length - 1;
    }
}
