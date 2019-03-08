package server;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

import java.io.*;
import java.net.*;


/**
 * Deals with sending data from the server to a client
 * or clients.
 *
 * */
class Sender implements MessageSender {
        private final String id;
        private String userName;
        private ObjectOutputStream out = null;

        Sender(Socket clientSocket) {

                id = clientSocket.getInetAddress().getHostAddress() + "_" + clientSocket.getPort();

                try {
                        out = new ObjectOutputStream(
                                        clientSocket.getOutputStream());
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }

        @Override
        public void postMessage(ChatContext chatContext, MessageBox messageBox) {
                if (isDirectMessage(messageBox)) {
                        sendDirectMessage(chatContext, messageBox);
                } else {
                        sendToAllInChat(chatContext, messageBox.get(Data.MESSAGE));
                }
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
        public String id() {
                return id;
        }

        @Override
        public void setUserName(String userName) {
                this.userName = userName;
        }

        @Override
        public String getUserName() {
                return userName;
        }

        /**
         * Sends a message to every other user in the passed-in chat.
         *
         * */
        private void sendToAllInChat(ChatContext chatContext, String message) {
                for (MessageSender sender : chatContext) {
                        if (notOriginalSender(sender)) {
                                MessageBox mb = new MessageBox(Action.CHAT);
                                mb.add(Data.MESSAGE, message);
                                mb.add(Data.USER_NAME, getUserName());
                                sender.sendMessage(mb);
                        }
                }
        }

        private void sendDirectMessage(ChatContext chatContext, MessageBox messageBox) {
                MessageSender dmTarget = chatContext.getUser(messageBox.get(Data.USER_NAME));
                dmTarget.sendMessage(messageBox);
        }

        private boolean notOriginalSender(MessageSender sender) {
                return !sender.getUserName().equals(getUserName());
        }

        private boolean isDirectMessage(MessageBox messageBox) {
                return messageBox.get(Data.USER_NAME) != null;
        }
}
