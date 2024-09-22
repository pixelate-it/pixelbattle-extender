package com.pixelbattle.extender.util;

public enum TransformType {
    LEFT_TOP("Left Top", 0),
    RIGHT_TOP("Right Top", 1),
    LEFT_BOTTOM("Left Bottom", 2),
    RIGHT_BOTTOM("Right Bottom", 3),
    CENTER("Center", 4);
    
    public final String name;
    public final int id;

    TransformType(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
