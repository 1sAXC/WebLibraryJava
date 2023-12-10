package com.webJava.library.helpers.impl;

import org.springframework.stereotype.Component;
import com.webJava.library.helpers.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ImageUtilsImpl implements ImageUtils {

    @Override
    public byte[] compress(byte[] imageData) {
        var deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(imageData);
        deflater.finish();

        var outputStream = new ByteArrayOutputStream(imageData.length);
        var tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            var size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }

        try {
            outputStream.close();
        } catch (Exception ignored) {
        }

        return outputStream.toByteArray();
    }

    @Override
    public byte[] decompress(byte[] imageData) {
        var inflater = new Inflater();
        inflater.setInput(imageData);

        var outputStream = new ByteArrayOutputStream(imageData.length);
        var tmp = new byte[4 * 1024];

        try {
            while (!inflater.finished()) {
                var size = inflater.inflate(tmp);
                outputStream.write(tmp, 0, size);
            }

            outputStream.close();
        } catch (Exception ignored) {
        }

        return outputStream.toByteArray();
    }
}
