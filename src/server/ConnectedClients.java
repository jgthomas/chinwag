package server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class ConnectedClients {
    private final ConcurrentMap<String, MessageHandler> clientsByID;
    private final ConcurrentMap<String, MessageHandler> clientsByUserName;

    ConnectedClients() {
        clientsByID = new ConcurrentHashMap<>();
        clientsByUserName = new ConcurrentHashMap<>();
     }

     void addClientByID(String id, MessageHandler messageHandler) {
        clientsByID.put(id, messageHandler);
     }

     void addClientByUserName(String id, String userName) {
        MessageHandler mh = clientsByID.get(id);
        clientsByUserName.put(userName, mh);
     }

     MessageHandler getClientByUserName(String userName) {
        return clientsByUserName.get(userName);
     }
}
