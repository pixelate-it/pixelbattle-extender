package com.pixelbattle.extender.objects;


import com.pixelbattle.extender.util.ExtendingError;
import com.pixelbattle.extender.util.Transform;

import java.util.List;

public class Canvas {
    public int width;
    public int height;
    public int last;
    Pixel[] pixels;
    Transform transform;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width * height];
        this.last = 0;
        this.transform = new Transform(0, 0);
    }

    public Pixel getPixel(int x, int y) throws ExtendingError {
        if (!isPixelInside(x, y)) {
            throw new ExtendingError("The pixel is outside the canvas bounds");
        }

        x -= transform.x;
        y -= transform.y;

        int index = x + (y * this.width);

        return this.pixels[index];
    }

    public void setPixels(List<Pixel> pixels) {
        for (int i = 0; i < pixels.size(); i++)
            this.pixels[i] = pixels.get(i);
    }

    public boolean isPixelInside(int x, int y) {
        if (this.width > 0 && this.height > 0)
            return x >= transform.x && y >= transform.y && x < transform.x + this.width && y < transform.y + this.height;
        return false;
    }

    public void applyTransform(Transform transform) {
        this.transform = transform;
    }
}
