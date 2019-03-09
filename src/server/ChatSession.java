package server;


import java.util.concurrent.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a chat session.
 *
 * Keeps a record of the MessageSender instances and the user names
 * of those threads involved in the chat.
 *
 * */
public class ChatSession implements ChatContext {
        private final String chatName;
        private final ConcurrentMap<String, MessageSender> usersInChat;

        ChatSession(String chatName) {
                this.chatName = chatName;
                usersInChat = new ConcurrentHashMap<>();
        }

        /**
         * Gets the chat session's name
         *
         * @return the name of the chat session
         **/
        @Override
        public String getChatName() {
                return chatName;
        }

        /**
         * Adds a user to the chat session by registering their message sender object
         *
         * @param messageSender the messageSender object to add
         * */

        @Override
        public void addUser(MessageSender messageSender) {
                usersInChat.put(messageSender.getUserName(), messageSender);
        }

        /**
         * Removes a user from a chat session by removing their message sender object
         *
         * @param messageSender the messageSender to remove
         * */
        @Override
        public void removeUser(MessageSender messageSender) {
                usersInChat.remove(messageSender.getUserName());
        }

        /**
         * Gets a list of all the users currently in the chat
         *
         * @return a list of user names
         * */
        @Override
        public List<String> allUserNames() {
                List<String> names = new ArrayList<>(usersInChat.keySet());
                Collections.sort(names);
                return names;
        }

        /**
         * Allows all the message senders registered with this chat session
         * to be iterated over in a for-each loop directly from the chat object
         *
         * Example:
         *
         * for (MessageSender sender : chatContext) {
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
