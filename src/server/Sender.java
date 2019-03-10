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

        Sender(Socket clientSocket, String socketID) {
                id = socketID;

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
        public void postMessage(ChatSession chatSession, MessageBox messageBox) {
                MessageBox outMessage = buildMessage(messageBox);
                for (MessageSender sender : chatSession) {
                        if (notOriginalSender(sender)) {
                                sender.sendMessage(outMessage);
                        }
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

        private MessageBox buildMessage(MessageBox messageBox) {
                MessageBox mb = new MessageBox(Action.CHAT);
                mb.add(Data.MESSAGE, messageBox.get(Data.MESSAGE));
                mb.add(Data.USER_NAME, getUserName());
                mb.add(Data.CHAT_NAME, messageBox.get(Data.CHAT_NAME));
                return mb;
        }

        private boolean notOriginalSender(MessageSender sender) {
                return !sender.getUserName().equals(getUserName());
        }
}
