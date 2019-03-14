package server;

import protocol.Data;
import protocol.MessageBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * The client loads an image into a Byte[], which is converted into a
 * string on the client end with the separator " ", and adds the string
 * to the message box, specifying the chat name and that the string is
 * an image.
 *
 * When the server receives the message box, it generates an image command.
 * In the execute method of image command, first it extracts the string
 * data of the image and the chat name from the message box, then it splits
 * the string of image and parse it into byte, and stores it in a byte[].
 * After that, the file output stream writes the file to the directory image/filename.
 * Finally, the message box is sent to other clients in the same group chat.
 */

public class ImageCommand extends Command {

    ImageCommand(MessageSender messageSender,
                UserChatSessions userChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    /**
     * This method generates a filename, according to the current time, so that
     * every file has a distinguished file name.
     *
     * @return  A file name of string type
     */
    private String generateFileName(){
        return new Timestamp(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * This method splits a string into an array, using a " " separator, then
     * every string element in the array is parsed into byte and assigned to a
     * byte array. Finally the byte array is returned.
     *
     * @param string    The string contains all the information for the image,
     *                  which was converted from byte[] on the client end.
     * @return          A Byte array
     */
    private byte[] stringToArray(String string){
        String[] tem = string.split(" ");
        int len = tem.length;
        byte[] by = new byte[len];
        for (int i = 0; i < len; i++) {
            by[i] = Byte.parseByte(tem[i]);
        }
        return by;
    }

    /**
     * Stores image in a local server directory "image" and sends the message box to
     * other members in the group chat.
     *
     * @param messageBox the command from the client to perform
     */
    @Override
    public void execute(MessageBox messageBox){
        try {
            String fileName = generateFileName();
            String image = messageBox.get(Data.IMAGE);
            String chatname = messageBox.get(Data.CHAT_NAME);
            byte[] by = stringToArray(image);
            int len = by.length;
            File file = new File("image/" + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            if (len != 0) {
                fos.write(by, 0, len);
            }
            // The following two lines are to send the image to all other users in the same group chat
            ChatSession chatSession = getAllChatSessions().getSession(chatname);
            getMessageSender().postMessage(chatSession, messageBox);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
