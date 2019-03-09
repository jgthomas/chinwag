package server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Keeps track of all the chat sessions this client is
 * currently engaged involved with.
 *
 * */
class Sessions implements SessionTracker {
        private final ConcurrentMap<String, ChatContext> activeSessions;
        private String currentSession;
        
        Sessions(ChatContext global) {
                activeSessions = new ConcurrentHashMap<>();
                activeSessions.put("global", global);
                currentSession = "global";
        }

        /**
         * @param chat a chat session to add to the client's
         *             current sessions
         */
        @Override
        public void addSession(ChatContext chat) {
                activeSessions.put(chat.getName(), chat);
        }

        /**
         * @param sessionName the name of the chat session to remove
         *                    from the client's current sessions
         */
        @Override
        public void removeSession(String sessionName) {
                activeSessions.remove(sessionName);
        }

        /**
         * @param sessionName the name of the chat session
         * @return a chat session object
         */
        @Override
        public ChatContext getSession(String sessionName) {
                return activeSessions.get(sessionName);
        }

        /**
         * @return the current chat session object
         */
        @Override
        public ChatContext getCurrentSession() {
                return activeSessions.get(getCurrentSessionName());
        }

        /**
         * @return the name of the current chat session
         */
        @Override
        public String getCurrentSessionName() {
                return currentSession;
        }

        /**
         * Removes the message sender object from all chat sessions
         * it is currently registered with.
         *
         * @param messageSender the message sender object to remove
         */
        @Override
        public void exitAll(MessageSender messageSender) {
                for (ChatContext chat : this) {
                        chat.removeUser(messageSender);
                }
        }

        /**
         * Allows the chat sessions to be iterated over in a for-each loop
         * directly from the session tracker object.
         *
         * Example:
         *
         * for (ChatContext chat : sessionTracker) {
         *     do stuff...
         * }
         *
         * @return an iterator over all the chat sessions stored in the
         *         activeSessions map
         */
        @Override
        public Iterator<ChatContext> iterator() {
                return activeSessions.values().iterator();
        }
}

