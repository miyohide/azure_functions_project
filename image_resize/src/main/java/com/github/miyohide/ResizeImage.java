package com.github.miyohide;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResizeImage {
    public static byte[] resize(byte[] original) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(original));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(image)
                .scale(0.50)
                .toOutputStream(out);
        return out.toByteArray();
    }
}
