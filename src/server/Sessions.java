package server;

import java.util.*;


class Sessions implements SessionTracker {
        private final Map<String, ChatContext> activeSessions;
        private final MessageSender messageSender;
        private String currentSession;

        Sessions(MessageSender messageSender, ChatContext global) {
                this.messageSender = messageSender;
                activeSessions = new HashMap<>();
                activeSessions.put("global", global);
                currentSession = "global";
        }

        @Override
        public void addSession(String sessionName, ChatContext chat) {
                activeSessions.put(sessionName, chat);
                setCurrentSessionName(sessionName);
        }

        @Override
        public void removeSession(String sessionName) {
                activeSessions.remove(sessionName);
        }

        @Override
        public ChatContext getCurrentSession() {
                return activeSessions.get(getCurrentSessionName());
        }

        @Override
        public ChatContext getSession(String sessionName) {
                return activeSessions.get(sessionName);
        }

        @Override
        public String getCurrentSessionName() {
                return currentSession;
        }

        @Override
        public void setCurrentSessionName(String sessionName) {
                if (activeSessions.keySet().contains(sessionName)) {
                        currentSession = sessionName;
                }
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

