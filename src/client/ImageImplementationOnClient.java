package client;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Please feel free to delete this class. It's only used to show
 * the implementation of conversion between an image and a string
 * and sending and receiving images through a messageBox.
 *
 * This class specifies that in the messageBox, the server would expect
 * a string of image and the chat name the image is to be sent to.
 */
public class ImageImplementationOnClient {

    /**
     * This method is for when a client wants to send an image,
     * and a local image needs to be converted into a string.
     * When this message is called with the path of the image
     * provided, the return value would be the string of the
     * image converted.
     *
     * Note that the image file can be jpg, png, bmp or gif.
     * But later I've decided that we output it as jpg, so
     * that it's easier to be managed.
     *
     * @param path  The path of the image file
     * @return      The string value of the image converted into
     */
    public String imageToString(String path){
        String image = null;
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            BufferedImage bufferedImage;
            File file = new File(path);
            bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", bs);
            byte[] bytes = bs.toByteArray();
            image = encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Unfamiliar with the implementation on the client end, this
     * method is written to mimic the action on sending an image.
     * First it calls imageToString method to convert an image to
     * String, then adds the string to a messageBox, together with
     * the chat name the sender wants it to be sent to. Finally it
     * sends out the messageBox.
     *
     * @param path      The path of the image file
     * @param chatName  The chat name that the user wants the image to be sent to
     */
    public void sendImage(String path, String chatName){
        MessageBox messageBox = new MessageBox(Action.SEND_IMAGE);
        messageBox.add(Data.IMAGE, imageToString(path));
        messageBox.add(Data.CHAT_NAME, chatName);
        // objectOuputStream.write(messageBox);
    }

    /**
     * This is for when a client receives a messageBox containing a
     * string of image. It converts the string back to an image and
     * stores it in an image buffer. Then it creates a file according
     * to the path the user enters (or could be any client-designated
     * path). If the path already exists, it throws an exception, if
     * not, it writes the image to that file.
     *
     * However, please note that this method downloads the image to
     * local directory, so the GUI might need to load the downloaded
     * image to a certain JavaFX component.
     *
     * @param image     The string format of the image sent to the client
     * @param path      The path that the image is stored into
     */
    public void stringToImage(String image, String path){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(image);
            ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
            BufferedImage bi =ImageIO.read(bs);
            File file = new File(path);
            if (file.exists())
                throw new FileAlreadyExistsException("");
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
