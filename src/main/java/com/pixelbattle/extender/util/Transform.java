package com.pixelbattle.extender.util;

public class Transform {
    public int x;
    public int y;

    public Transform(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Transform fromIntToTransform(int positioning) {
        return switch (positioning) {
            default -> null;
            case 1 -> new Transform(Config.instance.outputCanvasWidth - Config.instance.initialCanvasWidth, 0);
            case 2 -> new Transform(0, Config.instance.outputCanvasHeight - Config.instance.initialCanvasHeight );
            case 3 ->
                    new Transform(Config.instance.outputCanvasWidth - Config.instance.initialCanvasWidth, Config.instance.outputCanvasHeight - Config.instance.initialCanvasHeight);
            case 4 ->
                    new Transform((int) Math.floor((double) (Config.instance.outputCanvasWidth - Config.instance.initialCanvasWidth) / 2), (int) Math.floor((double) (Config.instance.outputCanvasHeight - Config.instance.initialCanvasHeight) / 2));
        };
    }
}

