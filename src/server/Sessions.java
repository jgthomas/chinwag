package server;

import java.util.*;


class Sessions implements SessionTracker {
        private final Map<String, ChatContext> activeSessions;
        private final MessageSender messageSender;

        Sessions(MessageSender messageSender, ChatContext global) {
                this.messageSender = messageSender;
                activeSessions = new HashMap<>();
                activeSessions.put("global", global);
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

