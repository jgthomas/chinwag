package server;


import java.util.concurrent.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a chat session.
 *
 * Keeps a record of the MessageSender instances and the user names
 * of those threads involved in the chat.
 *
 * A connected client joins a chat by registering its message sender object
 * and leaves a chat by removing its message sender object.
 *
 * */
public class ChatSession implements Iterable<MessageSender> {
        private final String chatName;
        private final ConcurrentMap<String, MessageSender> usersInChat;

        public ChatSession(String chatName) {
                this.chatName = chatName;
                usersInChat = new ConcurrentHashMap<>();
        }

        /**
         * Gets the chat session's name
         *
         * @return the name of the chat session
         **/
        public String getChatName() {
                return chatName;
        }

        /**
         * Adds a user to the chat session by registering their message sender object
         *
         * @param messageSender the messageSender object to add
         * */
        public void addUser(MessageSender messageSender) {
                usersInChat.put(messageSender.getUserName(), messageSender);
        }

        /**
         * Removes a user from a chat session by removing their message sender object
         *
         * @param messageSender the messageSender to remove
         * */
        public void removeUser(MessageSender messageSender) {
                usersInChat.remove(messageSender.getUserName());
        }

        /**
         * Checks if the chat is now empty
         *
         * @return true if empty, else false
         * */
        public boolean isEmpty() {
                return usersInChat.size() == 0;
        }

        /**
         * Removes all users from the chat session
         *
         * */
        void removeAllUsers() {
                for (MessageSender sender : this) {
                        removeUser(sender);
                }
        }

        /**
         * Gets a list of all the users currently in the chat
         *
         * @return a list of user names
         * */
        public List<String> allUserNames() {
                return new ArrayList<>(usersInChat.keySet());
        }

        /**
         * Allows all the message senders registered with this chat session
         * to be iterated over in a for-each loop directly from the chat object
         *
         * Example:
         *
         * for (MessageSender sender : chatSession) {
         *     do stuff...
         * }
         *
         * @return an iterator over all the message senders stored in the
         *         connectedClients map
         */
        @Override
        public Iterator<MessageSender> iterator() {
                return usersInChat.values().iterator();
        }
}
