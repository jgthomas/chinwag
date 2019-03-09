package server;

import protocol.MessageBox;

abstract class Command {
        private final MessageSender messageSender;
        private final CurrentChatSessions currentChatSessions;
        private final ConnectedClients connectedClients;

        Command(MessageSender messageSender,
                CurrentChatSessions currentChatSessions,
                ConnectedClients connectedClients)
        {
                this.messageSender = messageSender;
                this.currentChatSessions = currentChatSessions;
                this.connectedClients = connectedClients;
        }

        /**
         * @return the message sender object for use with this command
         * */
        MessageSender getMessageSender() {
                return messageSender;
        }

        /**
         * @return the session tracker object for use with this command
         * */
        CurrentChatSessions getCurrentChatSessions() {
                return currentChatSessions;
        }

        /**
         * @return an object tracking all the connected clients
         * */
        ConnectedClients getConnectedClients() {
                return connectedClients;
        }

        /**
         * Gets an single user from those currently connected
         *
         * @param userName the name of the user to get
         * @return a user's message handler object
         * */
        MessageHandler getUser(String userName) {
                return connectedClients.getClientByUserName(userName);
        }

        /**
         * Adds a user to a chat session
         *
         * @param chatSession the chat the user is joining
         * @param messageHandler the user to join the chat
         */
        void addUserToChat(ChatSession chatSession, MessageHandler messageHandler) {
                chatSession.addUser(messageHandler.getMessageSender());
                messageHandler.getCurrentChatSessions().addSession(chatSession);
        }

        /**
         * Executes the command received from the client.
         * Overridden in subclasses.
         *
         * @param messageBox the command from the client to perform
         * */
        abstract void execute(MessageBox messageBox);
}
