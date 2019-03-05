
import java.io.*;
import java.net.*;


/**
 * Deals with receiving data from the client.
 *
 * When data is recieved, in the form of a MessageBox object,
 * it is passed to the messageHandler.
 *
 * Runs until it recieves the command to quit.
 *
 * */
public class Receiver implements MessageReceiver {
        private final Socket clientSocket;
        private final MessageHandler messageHandler;

        public Receiver(Socket clientSocket, MessageHandler messageHandler) {
                this.clientSocket = clientSocket;
                this.messageHandler = messageHandler;
        }

        @Override
        public void listeningLoop() {
                MessageBox messageBox = null;
                try (ObjectInputStream in =
                                new ObjectInputStream(clientSocket.getInputStream()))
                {
                        do {
                                try {
                                        messageBox = (MessageBox) in.readObject();

                                        if (messageBox.getCommand() != Action.QUIT) {
                                                messageHandler.handle(messageBox);
                                        }

                                } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                }
                        } while (messageBox.getCommand() != Action.QUIT);
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }
}
