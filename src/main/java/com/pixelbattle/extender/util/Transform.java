package com.pixelbattle.extender.util;

public class Transform {
    public int x;
    public int y;

    public Transform(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Transform fromIntToTransform(int positioning, int width, int height) {
        return switch (positioning) {
            default -> null;
            case 1 -> new Transform(RuntimeProperties.outputCanvasWidth - width, 0);
            case 2 -> new Transform(0, RuntimeProperties.outputCanvasHeight - height );
            case 3 ->
                    new Transform(RuntimeProperties.outputCanvasWidth - width, RuntimeProperties.outputCanvasHeight - height);
            case 4 ->
                    new Transform((int) Math.floor((double) (RuntimeProperties.outputCanvasWidth - width) / 2), (int) Math.floor((double) (RuntimeProperties.outputCanvasHeight - height) / 2));
        };
    }
}

