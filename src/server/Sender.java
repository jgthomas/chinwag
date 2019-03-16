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
         * @param chatSession the chat to which the message should be sent
         * @param messageBox the message content to send
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
         * @param messageBox the message content to send
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

        /**
         * Closes the sender object
         *
         * */
        @Override
        public void closeSender() {
                try {
                        out.close();
                } catch (IOException ioException) {
                        ioException.printStackTrace();
                }
        }

        /**
         * Gets the client's ID
         *
         * @return the client's ID
         */
        @Override
        public String id() {
                return id;
        }

        /**
         * Sets the client's username
         *
         * @param userName the new username
         * */
        @Override
        public void setUserName(String userName) {
                this.userName = userName;
        }

        /**
         * Gets the client's username
         *
         * @return the client's username
         * */
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
