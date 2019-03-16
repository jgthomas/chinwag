package server;

import protocol.Data;
import protocol.MessageBox;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;

/**
 * The client loads an image and converts it to a string, which is added to
 * the message box together with the format of the image and the chat name
 * to send to.
 *
 * When the server receives the message box, it generates an image command.
 * In the execute method of image command, first it extracts the string
 * data of the image, the format of the image and the chat name from the
 * message box, then it converts the string of the image back to an image,
 * and the ImageIO writes the image to the directory image/filename. Finally,
 * the message box is sent to other clients in the same group chat.
 */

public class ImageCommand extends Command {

    ImageCommand(MessageSender messageSender,
                UserState userState,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * This method generates a filename, according to the current time, so that
     * every file has a distinct file name.
     *
     * @return  A file name of string type
     */
    private String generateFileName(){
        return new Timestamp(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * This method converts the string converted from an image back to the image.
     * According to the image formate passed to it, it converts the string to either
     * jpg, png or gif.
     *
     * @param string
     * @param path
     */
    public void stringToImage(String string, String imageFormat, String path){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(string);
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
            }
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores image in a local server directory "image" and sends the message box to
     * other members in the group chat.
     *
     * @param messageBox the command from the client to perform
     */
    @Override
    public void execute(MessageBox messageBox){
        String fileName = generateFileName();
        String image = messageBox.get(Data.IMAGE);
        String imageFormat = messageBox.get(Data.IMAGE_FORMAT);
        String chatname = messageBox.get(Data.CHAT_NAME);
        String path = "src/server/image/" + fileName;
        stringToImage(image, imageFormat, path);
        // The following two lines are to send the image to all other users in the same group chat
        ChatSession chatSession = getAllChatSessions().getSession(chatname);
        getMessageSender().postMessage(chatSession, messageBox);
    }
}
