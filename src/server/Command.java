package server;

import protocol.MessageBox;

abstract class Command {
        private final MessageSender messageSender;
        private final UserChatSessions userChatSessions;
        private final ConnectedClients connectedClients;
        private final AllChatSessions allChatSessions;

        Command(MessageSender messageSender,
                UserChatSessions userChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
        {
                this.messageSender = messageSender;
                this.userChatSessions = userChatSessions;
                this.allChatSessions = allChatSessions;
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
        UserChatSessions getUserChatSessions() {
                return userChatSessions;
        }

        /**
         * @return an object tracking all the connected clients
         * */
        ConnectedClients getConnectedClients() {
                return connectedClients;
        }

        /**
         * @return an object tracking all the chats on the server
         * */
        AllChatSessions getAllChatSessions() {
                return allChatSessions;
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
         * Adds a newly created chat to the master record, AND to the
         * chats of the current user
         *
         * @param chatSession the session to add to the master record
         * */
        void registerNewChat(ChatSession chatSession) {
                getAllChatSessions().addSession(chatSession);
                getUserChatSessions().addSession(chatSession);
        }

        /**
         * Adds a user to an *existing* chat session
         *
         * @param chatName the chat the user is joining
         * @param messageHandler the user to join the chat
         */
        void addUserToChat(String chatName, MessageHandler messageHandler) {
                ChatSession chatSession = getAllChatSessions().getSession(chatName);
                chatSession.addUser(messageHandler.getMessageSender());
                messageHandler.getUserChatSessions().addSession(chatSession);
        }

        /**
         * Remove a user from an *existing* chat session
         *
         * @param chatName the chat from which the user is to be removed
         * @param messageHandler the user to remove
         * */
        void removeUserFromChat(String chatName, MessageHandler messageHandler) {
                ChatSession chatSession = getAllChatSessions().getSession(chatName);
                chatSession.removeUser(messageHandler.getMessageSender());
                messageHandler.getUserChatSessions().removeSession(chatName);
        }

        /**
         * Executes the command received from the client.
         * Overridden in subclasses.
         *
         * @param messageBox the command from the client to perform
         * */
        abstract void execute(MessageBox messageBox);
}
