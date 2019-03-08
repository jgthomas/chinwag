package server;

import java.util.List;

public interface Chatty {

    String getName();

    void addUser(MessageHandler messageHandler);

    void removeUser(MessageHandler messageHandler);

    List<String> allUserNames();

    MessageSender getUser(String userName);
}
