package com.pixelbattle.extender.logic;

import com.google.gson.*;
import com.pixelbattle.extender.objects.Color;
import com.pixelbattle.extender.objects.Pixel;
import com.pixelbattle.extender.util.RuntimeProperties;

import java.lang.reflect.Type;
import java.util.HashMap;


public class CanvasDeserializer implements JsonDeserializer<Pixel> {
    public Long width = 1L;
    public Long height = 1L;
    public HashMap<String, Integer> tagLeaders = new HashMap<String, Integer>();

    @Override
    public Pixel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        if (!obj.has("x") || !obj.has("y") || !obj.has("color") ||
                !obj.has("author") || !obj.has("tag")) {
            throw new JsonParseException("Missing required fields");
        }

        {
            JsonElement x = this.unnullable(obj.get("x"));
            JsonElement y = this.unnullable(obj.get("y"));
            JsonElement color = this.unnullable(obj.get("color"));
            JsonElement author = this.nullable(obj.get("author"));
            JsonElement tag = this.nullable(obj.get("tag"));

            if (!isNumber(x) || !isNumber(y))
                throw new JsonParseException("Values of x,y must be number. Try to find this.");

            if (isNotStringOrNull(author) || isNotStringOrNull(tag))
                throw new JsonParseException("Values of author,tag must be string or null. Try to find this.");

            Long xValue = x.getAsLong();
            Long yValue = y.getAsLong();
            Color colorValue;
            String authorValue = getString(author);
            String tagValue = getString(tag);

            if (isNumber(color)) {
                colorValue = new Color(color.getAsInt());
            } else if (isString(color)) {
                colorValue = new Color(color.getAsString());
            } else {
                throw new JsonParseException("Value of color must be number or string. Try to find this.");
            }

            if (xValue < 0 || yValue < 0) {
                throw new JsonParseException("Negative coordinates are not allowed");
            }

            if (this.width < xValue)
                this.width = xValue + 1;
            if (this.height < yValue)
                this.height = yValue + 1;

            return new Pixel(xValue, yValue, authorValue, tagValue, colorValue);
        }
    }

    private void primitive(JsonElement element) throws JsonParseException {
        if (!element.isJsonPrimitive() && !element.isJsonNull()) throw new JsonParseException("Value must be primitive like boolean, number, other.");
    }

    private JsonElement nullable(JsonElement element) throws JsonParseException {
        this.primitive(element);
        return element;
    }

    private String getString(JsonElement element) {
        return element.isJsonNull() ? null : element.getAsString();
    }

    private boolean isNotStringOrNull(JsonElement element) {
        if (element.isJsonNull())
            return false;
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        return !primitive.isNumber();
    }

    private boolean isNumber(JsonElement element) {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        return primitive.isNumber();
    }

    private boolean isString(JsonElement element) {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        return primitive.isString();
    }

    private JsonElement unnullable(JsonElement element) throws JsonParseException {
        this.primitive(element);
        if (element.isJsonNull())
            throw new JsonParseException("Element of canvas cannot be null!");
        return element;
    }
}
