package server;


import java.util.concurrent.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a chat session.
 *
 * Keeps a record of the MessageSender instances and the user names
 * of those threads involved in the chat.
 *
 * */
public class ChatSession implements ChatContext {
        private final String name;
        private final ConcurrentMap<String, MessageSender> connectedClients;

        ChatSession(String name) {
                this.name = name;
                connectedClients = new ConcurrentHashMap<>();
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public void addUser(MessageSender messageSender) {
                connectedClients.put(messageSender.getUserName(), messageSender);
        }

        @Override
        public void removeUser(MessageSender messageSender) {
                connectedClients.remove(messageSender.getUserName());
        }

        @Override
        public List<String> allUserNames() {
                List<String> names = new ArrayList<>(connectedClients.keySet());
                Collections.sort(names);
                return names;
        }

        @Override
        public MessageSender getUser(String userName) {
                return connectedClients.get(userName);
        }

        @Override
        public Iterator<MessageSender> iterator() {
                return connectedClients.values().iterator();
        }
}
