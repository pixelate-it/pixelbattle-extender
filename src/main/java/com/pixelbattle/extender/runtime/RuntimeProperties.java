package com.pixelbattle.extender.runtime;

import com.pixelbattle.extender.primitives.Color;
import com.pixelbattle.extender.primitives.Size;

public class RuntimeProperties {
    public static int       CHUNK_LENGTH        = 500000;
    public static Color     BASIC_FILL_COLOR    = new Color("#FFFFFF");
    public static Size      OLD_SIZE            = new Size(1000,1000);
    public static Size      EXTEND_SIZE         = new Size(1000,1000);
    public static String    EXTENDED_PATH       = "results/canvas";
    public static String    TAGS_PATH           = "results/tags";
    public static String    IMAGES_PATH         = "results/images";
    public static String    RESULT_PATH         = "results";
    public static Type.CanvasPosition POSITION  = Type.CanvasPosition.LEFT_TOP;
}
