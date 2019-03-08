package server;

import java.util.List;

public interface Chatty extends Iterable<MessageHandler> {

    String getName();

    void addUser(MessageHandler messageHandler);

    void removeUser(MessageHandler messageHandler);

    List<String> allUserNames();

    MessageHandler getUser(String userName);
}
