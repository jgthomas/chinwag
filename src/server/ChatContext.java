package server;

import java.util.List;
import java.util.Iterator;


public interface ChatContext extends Iterable<MessageSender> {

        String getName();

        void addUser(MessageSender messageSender);

        void removeUser(MessageSender messageSender);

        List<String> allUserNames();
}
