package server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Keeps track of all the chat sessions this client is
 * currently engaged involved with.
 *
 * */
class UserState implements Iterable<ChatSession> {
        private final ConcurrentMap<String, ChatSession> activeSessions;
        private final Set<String> allFriends;

        UserState() {
                activeSessions = new ConcurrentHashMap<>();
                allFriends = new HashSet<String>();
        }

        UserState(ChatSession global) {
                activeSessions = new ConcurrentHashMap<>();
                allFriends = new HashSet<String>();
                activeSessions.put("global", global);
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
        
        
        
        void addFriend(String friend) {
        	allFriends.add(friend);
        }
        
        void removeFriend(String friend) {
        	allFriends.remove(friend);
        }
        

        public List<String> getAllFriends() {
			return new ArrayList<>(allFriends);
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
         * Gets a list of sessions the user is in
         *
         * @return list of all sessions the user is currently in
         * */
        List<String> allUserChatSessions() {
                return new ArrayList<>(activeSessions.keySet());
        }

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

