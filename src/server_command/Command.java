package server_command;

import protocol.MessageBox;
import server.*;

public abstract class Command {
        private final MessageSender messageSender;
        private final UserState userState;
        private final ConnectedClients connectedClients;
        private final AllChatSessions allChatSessions;

        public Command(MessageSender messageSender,
                       UserState userState,
                       AllChatSessions allChatSessions,
                       ConnectedClients connectedClients)
        {
                this.messageSender = messageSender;
                this.userState = userState;
                this.allChatSessions = allChatSessions;
                this.connectedClients = connectedClients;
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
                return getConnectedClients().getClientByUserName(userName);
        }

        /**
         * Adds a newly created chat to the system
         *
         * @param chatSession the chat object to add
         * */
        void registerChatOnSystem(ChatSession chatSession) {
                getAllChatSessions().addSession(chatSession);
        }

        /**
         * Registers a user with a chat session
         *
         * @param chatSession the chat session to join
         * */
        void registerUserWithChat(ChatSession chatSession) {
                chatSession.addUser(getMessageSender());
                getUserState().addSession(chatSession);
        }

        /**
         * Removes the current user from a chat
         *
         * @param chatSession the chat to leave
         * */
        void removeUserFromChat(ChatSession chatSession) {
                if (getUserState().isInChat(chatSession.getChatName())) {
                     chatSession.removeUser(getMessageSender());
                     getUserState().removeSession(chatSession);
                }
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
        UserState getUserState() {
                return userState;
        }

        /**
         * Convenience method to get the user name, as this is done very often
         *
         * @return the user name associated with the current thread
         * */
        String getCurrentThreadUserName() {
                return getMessageSender().getUserName();
        }

        /**
         * Convenience method to get the id, as this is done very often
         *
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
        public abstract void execute(MessageBox messageBox);
}
