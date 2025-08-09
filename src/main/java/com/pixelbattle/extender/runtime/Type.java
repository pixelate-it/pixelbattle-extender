package com.pixelbattle.extender.runtime;

import com.pixelbattle.extender.primitives.Position;
import com.pixelbattle.extender.primitives.Size;

public class Type {

    public enum Page {
        EXTEND("Extend"),
        IMAGE("Image"),
        TAGS("Tags"),
        EMPTY("Empty");

        public final String tabName;

        Page(String tabName) {
            this.tabName = tabName;
        }
    }

    public enum Image {
        PNG("PNG", ".png"),
        JPEG("JPEG", ".jpeg");

        public final String name;
        public final String suffix;

        Image(String name, String suffix) {
            this.name = name;
            this.suffix = suffix;
        }
    }

    public enum Tags  {
        JSON("JSON", ".json"),
        TABLE("Simple table", ".table");

        public final String name;
        public final String suffix;

        Tags(String name, String suffix) {
            this.name = name;
            this.suffix = suffix;
        }
    }

    public enum CanvasPosition {
        LEFT_TOP("Left Top", 0),
        RIGHT_TOP("Right Top", 1),
        LEFT_BOTTOM("Left Bottom", 2),
        RIGHT_BOTTOM("Right Bottom", 3),
        CENTER("Center", 4);

        public final String name;
        public final int id;

        CanvasPosition(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public Position toPosition(Size oldSize, Size newSize) {
            return switch (this.id) {
                case 1 -> new Position(newSize.width - oldSize.width, 0);
                case 2 -> new Position(0, newSize.height - oldSize.height);
                case 3 ->
                        new Position(newSize.width - oldSize.width, newSize.height - oldSize.height);
                case 4 ->
                        new Position((int) Math.floor((double) (newSize.width - oldSize.width) / 2), (int) Math.floor((double) (newSize.height - oldSize.height) / 2));
                default -> new Position(0,0);
            };
        }
    }

}
