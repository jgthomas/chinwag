package server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


class Sessions implements SessionTracker {
        private final ConcurrentMap<String, ChatContext> activeSessions;
        private String currentSession;
        
        Sessions(ChatContext global) {
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
        public void exitAll(MessageSender messageSender) {
                for (ChatContext chat : this) {
                        chat.removeUser(messageSender);
                }
        }

        @Override
        public Iterator<ChatContext> iterator() {
                return activeSessions.values().iterator();
        }
}

