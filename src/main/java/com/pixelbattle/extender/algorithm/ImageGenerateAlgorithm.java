package com.pixelbattle.extender.algorithm;

import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.primitives.Position;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.Type;
import com.pixelbattle.extender.wrappers.ChunkReadWrapper;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class ImageGenerateAlgorithm {
    public BufferedReader reader;
    public OutputStream outputStream;
    public ChunkReadWrapper wrapper;
    public Size size;

    public ImageGenerateAlgorithm(BufferedReader reader, OutputStream outputStream, Size size) throws IOException {
        this.reader = reader;
        this.outputStream = outputStream;
        this.wrapper = new ChunkReadWrapper(reader);
        this.size = size;
    }

    public void run(Type.Image typeImage) throws IOException {
        BufferedImage image = new BufferedImage(this.size.width, this.size.height, BufferedImage.TYPE_INT_RGB);

        while (this.wrapper.isNotEnd()) {
            Pixel pixel = this.wrapper.getPixel();
            if (this.wrapper.next()) {
                Position position = pixel.getPosition();
                image.setRGB(Math.toIntExact(position.x), Math.toIntExact(position.y), pixel.color.toInt());
            }
        }

        ImageOutputStream stream = ImageIO.createImageOutputStream(outputStream);
        ImageIO.write(image, typeImage.name.toLowerCase(), stream);
        stream.close();
    }
}
