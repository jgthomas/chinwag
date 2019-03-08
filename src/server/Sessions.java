package server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


class Sessions implements SessionTracker {
        private final ConcurrentMap<String, ChatContext> activeSessions;
        private final MessageSender messageSender;
        private final ConnectedClients connectedClients;
        private String currentSession;

        
        Sessions(MessageSender messageSender, ChatContext global, ConnectedClients connectedClients) {
                this.messageSender = messageSender;
                this.connectedClients = connectedClients;
                activeSessions = new ConcurrentHashMap<>();
                activeSessions.put("global", global);
                currentSession = "global";
        }

        @Override
        public void addSession(ChatContext chat) {
                activeSessions.put(chat.getName(), chat);
        }

        @Override
        public void removeSession(String sessionName) {
                activeSessions.remove(sessionName);
        }

        @Override
        public ChatContext getSession(String sessionName) {
                return activeSessions.get(sessionName);
        }

        @Override
        public void addUserToChat(String chatName, String userName) {
                MessageSender sender = getSession("global").getUser(userName);
                getSession(chatName).addUser(sender);
        }

        @Override
        public ChatContext getCurrentSession() {
                return activeSessions.get(getCurrentSession());
        }

        @Override
        public String getCurrentSessionName() {
                return currentSession;
        }

        @Override
        public void exitAll() {
                for (ChatContext chat : this) {
                        chat.removeUser(messageSender);
                }
        }

        @Override
        public ConnectedClients getConnectedClients() {
                return connectedClients;
        }

        @Override
        public Iterator<ChatContext> iterator() {
                return activeSessions.values().iterator();
        }
}

