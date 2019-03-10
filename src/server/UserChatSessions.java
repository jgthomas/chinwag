package server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Keeps track of all the chat sessions this client is
 * currently engaged involved with.
 *
 * */
class UserChatSessions implements Iterable<ChatSession> {
        private final ConcurrentMap<String, ChatSession> activeSessions;
        private String currentSession;
        
        UserChatSessions(ChatSession global) {
                activeSessions = new ConcurrentHashMap<>();
                activeSessions.put("global", global);
                currentSession = "global";
        }

        /**
         * @param chat a chat session to add to the client's
         *             current sessions
         */
        void addSession(ChatSession chat) {
                activeSessions.put(chat.getChatName(), chat);
        }

        /**
         * @param chatName the name of the chat session to remove
         *                    from the client's current sessions
         */
        void removeSession(String chatName) {
                activeSessions.remove(chatName);
        }

        /**
         * Checks if a user is in a chat
         *
         * @param chatName the name of the chat
         * @return true if user is in the chat, else false
         * */
        boolean isInChat(String chatName) {
                return activeSessions.containsKey(chatName);
        }

        /**
         * @param chatName the name of the chat session
         * @return a chat session object
         */
        ChatSession getSession(String chatName) {
                return activeSessions.get(chatName);
        }

        /**
         * @return the current chat session object
         */
        //ChatSession getCurrentSession() {
        //        return activeSessions.get(getCurrentSessionName());
        //}

        /**
         * @return the name of the current chat session
         */
        //String getCurrentSessionName() {
        //        return currentSession;
        //}

        /**
         * Removes the message sender object from all chat sessions
         * it is currently registered with.
         *
         * @param messageSender the message sender object to remove
         */
        void exitAll(MessageSender messageSender) {
                for (ChatSession chat : this) {
                        chat.removeUser(messageSender);
                }
        }

        /**
         * Allows all the chat sessions to be iterated over in a
         * for-each loop directly from the current sessions object.
         *
         *
         * Example:
         *
         * for (ChatContext chat : currentChatSessions) {
         *     do stuff...
         * }
         *
         * @return an iterator over all the chat sessions stored in the
         *         activeSessions map
         */
        @Override
        public Iterator<ChatSession> iterator() {
                return activeSessions.values().iterator();
        }
}

