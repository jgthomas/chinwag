package server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Keeps track of all the chat sessions this client is
 * currently engaged involved with.
 *
 * */
public class UserState implements Iterable<ChatSession> {
        private final ConcurrentMap<String, ChatSession> activeSessions;
        private final Set<String> allFriends;

        UserState() {
                activeSessions = new ConcurrentHashMap<>();
                allFriends = new HashSet<>();
        }

        /**
         * @param chat a chat session to add to the client's
         *             current sessions
         */
        public void addSession(ChatSession chat) {
                activeSessions.put(chat.getChatName(), chat);
        }

        /**
         * @param chatSession the chat session to remove
         *                    from the client's current sessions
         */
        public void removeSession(ChatSession chatSession) {
                activeSessions.remove(chatSession.getChatName());
        }

        /**
         * Adds a user to friends
         *
         * @param friendName the name of the friend to add
         * */
        public void addFriend(String friendName) {
                allFriends.add(friendName);
        }

        /**
         * Removes a user from friends
         *
         * @param friendName the name of the friend to remove
         * */
        public void removeFriend(String friendName) {
                allFriends.remove(friendName);
        }

        /**
         * Gets all the friends of the current user
         *
         * @return a list of all the user's friends
         * */
        public List<String> getAllFriends() {
			return new ArrayList<>(allFriends);
		}

		/**
         * Checks if a user is in a chat
         *
         * @param chatName the name of the chat
         * @return true if user is in the chat, else false
         * */
        public boolean isInChat(String chatName) {
                return activeSessions.containsKey(chatName);
        }

        /**
         * Gets a list of sessions the user is in
         *
         * @return list of all sessions the user is currently in
         * */
        public List<String> allUserChatSessions() {
                return new ArrayList<>(activeSessions.keySet());
        }

        /**
         * Removes the message sender object from all chat sessions
         * it is currently registered with.
         *
         * @param messageSender the message sender object to remove
         */
        public void exitAllChats(MessageSender messageSender) {
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

