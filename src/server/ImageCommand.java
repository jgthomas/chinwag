package server;

import database.Image;
import database.ImageQueue;
import protocol.Data;
import protocol.MessageBox;
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
    private String generateFileName(String imageFormat){
        switch (imageFormat){
            case "jpg":
                return new Timestamp(System.currentTimeMillis()) + ".jpg";
            case "png":
                return new Timestamp(System.currentTimeMillis()) + ".png";
            case "gif":
                return new Timestamp(System.currentTimeMillis()) + ".gif";
        }
        throw new IllegalArgumentException("Unsupported image format:" + imageFormat);
    }

    /**
     * Stores image in a local server directory "image" and sends the image message box to
     * other members in the group chat. The image saved will have a distinct file name.
     *
     * @param messageBox the command from the client to perform
     */
    @Override
    public void execute(MessageBox messageBox){

        String imageString = messageBox.get(Data.IMAGE);
        String imageFormat = messageBox.get(Data.IMAGE_FORMAT);
        String chatname = messageBox.get(Data.CHAT_NAME);
        String sender = getMessageSender().getUserName();

        // The following two lines are to send the image to all other users in the same group chat
        ChatSession chatSession = getAllChatSessions().getSession(chatname);
        getMessageSender().postMessage(chatSession, messageBox);

        String fileName = generateFileName(imageFormat);
        String path = "src/server/image/" + fileName;

        File file;
        file = new File(path);
        while(file.exists()){
            fileName = generateFileName(imageFormat);
            path = "src/server/image/" + fileName;
            file = new File(path);
        }
        Image image = new Image(imageString, imageFormat, chatname, sender, path, new Timestamp(System.currentTimeMillis()));
        ImageQueue.addToQueue(image);
    }
}
