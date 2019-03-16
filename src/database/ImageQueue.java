package database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;

public class ImageQueue implements Runnable {

    private static ConcurrentLinkedQueue<Image> insertionQueue = new ConcurrentLinkedQueue<>();

    public static void addToQueue(Image image) {
        insertionQueue.add(image);
    }

    /**
     * This method converts the string converted from an image back to the image.
     * According to the image formate passed to it, it converts the string to either
     * jpg, png or gif.
     *
     * @param image     The string that was converted from an image
     */
    public void stringToImage(Image image){
        String imageString = image.getImage();
        String imageFormat = image.getImageFormat();
        String path = image.getPath();
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(imageString);
            ByteArrayInputStream by = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(by);
            File file = new File(path);
            switch (imageFormat) {
                case "jpg":
                    ImageIO.write(bi, "jpg", file);
                case "png":
                    ImageIO.write(bi, "png", file);
                case "gif":
                    ImageIO.write(bi, "gif", file);
                default:
                    throw new IllegalArgumentException("Unsupported image format:" + imageFormat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if(!insertionQueue.isEmpty()) {
                Image image = insertionQueue.remove();
                Database.insertImage(image);
                stringToImage(image);
            }
        }
    }


}
