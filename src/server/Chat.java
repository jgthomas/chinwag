package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Chat implements Chatty {
    private final String name;
    private final ConcurrentMap<String, MessageHandler> connectedClients;

    Chat(String name) {
        this.name = name;
        connectedClients = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addUser(MessageHandler messageHandler) {
        connectedClients.put("q", messageHandler);
    }

    @Override
    public void removeUser(MessageHandler messageHandler) {
        connectedClients.remove("w");
    }

    @Override
    public List<String> allUserNames() {
        List<String> l = new ArrayList<>();
        return l;
    }

    @Override
    public MessageHandler getUser(String userName) {
        return connectedClients.get(userName);
    }

    @Override
    public Iterator<MessageHandler> iterator() {
        return connectedClients.values().iterator();
    }

}
