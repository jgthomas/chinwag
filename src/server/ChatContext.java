package server;

import java.util.List;


interface ChatContext extends Iterable<MessageSender> {

        String getName();

        void addUser(MessageSender messageSender);

        void removeUser(MessageSender messageSender);

        List<String> allUserNames();
}
