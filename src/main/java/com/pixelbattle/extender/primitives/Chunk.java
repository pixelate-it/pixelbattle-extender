package com.pixelbattle.extender.primitives;

public class Chunk {
    public Pixel[] pixels;
    public final int length;
    public int index;

    public Chunk(int length) {
        this.length = length;
        this.pixels = new Pixel[length];
        this.index = 0;
    }

    public Pixel getCurrentPixel() {
        return this.pixels[this.index];
    }

    public boolean next() {
        this.index++;
        if (this.index >= this.length) {
            this.index = this.length - 1;
            return false;
        }
        return true;
    }

    public void toStart() {
        this.index = 0;
    }

    public void setPixel(int index, Pixel pixel) {
        this.pixels[index] = pixel;
    }
}
