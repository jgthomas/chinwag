package database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;

public class ImageQueue implements Runnable {

    private static BlockingQueue<Image> insertionQueue = new LinkedBlockingQueue<>();

    public static void addToQueue(Image image) {
        try {
			insertionQueue.put(image);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
        String path = image.getPath();
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(imageString);
            ByteArrayInputStream by = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(by);
            File file = new File(path);
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void run() {
		while (true) {
			Image image;
			try {
				image = insertionQueue.take();
				Database.insertImage(image);
				stringToImage(image);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
