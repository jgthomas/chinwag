package server;


interface SessionTracker extends Iterable<ChatContext> {

        void addSession(ChatContext chatContext);

        void removeSession(String sessionName);

        ChatContext getSession(String sessionName);

        void exitAll();

        void addUserToChat(String chatName, String userName);

        ConnectedClients getConnectedClients();
}

