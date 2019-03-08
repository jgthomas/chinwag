package server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


class Sessions implements SessionTracker {
        private final ConcurrentMap<String, ChatContext> activeSessions;
        private final MessageSender messageSender;
        private String currentSession;

        
        Sessions(MessageSender messageSender, ChatContext global) {
                this.messageSender = messageSender;
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
        public ChatContext getCurrentSession() {
                return activeSessions.get(getCurrentSessionName());
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
        public Iterator<ChatContext> iterator() {
                return activeSessions.values().iterator();
        }
}

