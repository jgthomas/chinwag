package server;

import java.io.*;
import java.net.*;


/**
 * Deals with sending data from the server to a client
 * or clients.
 *
 * */
class Sender implements MessageSender {
        private final User user;
        private ObjectOutputStream out = null;

        Sender(Socket clientSocket, User user) {
                this.user = user;

                try {
                        out = new ObjectOutputStream(
                                        clientSocket.getOutputStream());
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }

        /**
         * Sends a message to every other user in the passed-in chat.
         *
         * */
        @Override
        public void postMessage(ChatContext chatContext, MessageBox messageBox) {
                sendToAllInChat(chatContext, messageBox.get(Data.MESSAGE));
        }

        /**
         * Sends a message to the current client.
         *
         * */
        @Override
        public void sendMessage(MessageBox messageBox) {
                try {
                        out.writeObject(messageBox);
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }

        @Override
        public void closeSender() {
                try {
                        out.close();
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }

        @Override
        public User getUser() {
                return user;
        }

        private void sendToAllInChat(ChatContext chatContext, String message) {
                for (MessageSender sender : chatContext) {
                        if (notOriginalSender(sender)) {
                                MessageBox mb = new MessageBox(Action.CHAT);
                                mb.add(Data.MESSAGE, message);
                                sender.sendMessage(mb);
                        }
                }
        }

        private String buildMessage(String message) {
                return user.getScreenName() + " >> " + message;
        }

        private boolean notOriginalSender(MessageSender sender) {
                return !sender.getUser().getScreenName().equals(user.getScreenName());
        }
}
