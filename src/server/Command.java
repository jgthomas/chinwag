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

        /* These methods work on ConnectedClients and AllChatSessions */

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
                return getConnectedClients().getClientByUserName(userName);
        }

        /**
         * Get a chat from the master record
         *
         * @param chatName the name of the chat
         * @return the chat session object
         * */
        ChatSession getChatSession(String chatName) {
                return getAllChatSessions().getSession(chatName);
        }

        /**
         * Delete a chat session entirely.
         *
         * Removes the chat session from every user and from the master record
         *
         * @param chatName the chat session to delete
         * */
        void deleteChatSession(String chatName) {
                ChatSession chatSession = getChatSession(chatName);

                for (String userName : chatSession.allUserNames()) {
                        MessageHandler user = getUser(userName);
                        user.getUserChatSessions().removeSession(chatName);
                }

                chatSession.removeAllUsers();
                getAllChatSessions().removeSession(chatName);
        }

        /**
         * Adds a DIFFERENT user to a chat session, pulling them in
         *
         * @param chatName the chat the user is joining
         * @param userName the name of the user
         */
        void addOtherUserToChat(String chatName, String userName) {
                MessageHandler user = getUser(userName);
                ChatSession chatSession = getChatSession(chatName);
                chatSession.addUser(user.getMessageSender());
                user.getUserChatSessions().addSession(chatSession);
        }

        /* These methods work on the state of the current thread */

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
         * @return the user name associated with the current thread
         * */
        String getCurrentThreadUserName() {
                return getMessageSender().getUserName();
        }

        /**
         * @return the id associated with the current thread
         */
        String getCurrentThreadID() {
                return getMessageSender().id();
        }

        /**
         * Executes the command received from the client.
         * Overridden in subclasses.
         *
         * @param messageBox the command from the client to perform
         * */
        abstract void execute(MessageBox messageBox);
}
