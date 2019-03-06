package server;


import java.util.concurrent.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a chat session.
 *
 * Keeps a record of the MessageSender instances and the usernames
 * of those threads involved in the chat.
 *
 * */
class ChatSession implements ChatContext {
        private final String name;
        private final ConcurrentMap<String, MessageSender> connectedClients;
        private final ConcurrentMap<String, String> activeUserNames;

        ChatSession(String name) {
                this.name = name;
                connectedClients = new ConcurrentHashMap<>();
                activeUserNames = new ConcurrentHashMap<>();
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public void addUser(MessageSender messageSender) {
                String id = messageSender.getUser().id();
                String name = messageSender.getUser().getName();
                connectedClients.put(id, messageSender);
                activeUserNames.put(name, id);
        }

        @Override
        public void removeUser(MessageSender messageSender) {
                String id = messageSender.getUser().id();
                String name = messageSender.getUser().getName();
                connectedClients.remove(id);
                activeUserNames.remove(name);
        }

        @Override
        public List<String> allUserNames() {
                List<String> names = new ArrayList<>(activeUserNames.keySet());
                Collections.sort(names);
                return names;
        }

        @Override
        public Iterator<MessageSender> iterator() {
                return connectedClients.values().iterator();
        }
}
