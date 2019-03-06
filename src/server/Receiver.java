package server;

import java.io.*;
import java.net.*;


/**
 * Deals with receiving data from the client.
 *
 * When data is received, in the form of a MessageBox object,
 * it is passed to the messageHandler.
 *
 * Runs until it receives the command to quit.
 *
 * */
class Receiver implements MessageReceiver {
        private final Socket clientSocket;
        private final MessageHandler messageHandler;

        Receiver(Socket clientSocket, MessageHandler messageHandler) {
                this.clientSocket = clientSocket;
                this.messageHandler = messageHandler;
        }

        @Override
        public void listeningLoop() {
                MessageBox messageBox = new MessageBox(Action.CHAT);
                try (ObjectInputStream in =
                                new ObjectInputStream(clientSocket.getInputStream()))
                {
                        do {
                                try {
                                        messageBox = (MessageBox) in.readObject();

                                        if (messageBox.getAction() != Action.QUIT) {
                                                messageHandler.handle(messageBox);
                                        }

                                } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                }
                        } while (messageBox.getAction() != Action.QUIT);
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }
}
