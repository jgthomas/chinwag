package server;


interface SessionTracker extends Iterable<ChatContext> {

        void addSession(ChatContext chatContext);

        void removeSession(String sessionName);

        ChatContext getSession(String sessionName);

        void exitAll();

        ConnectedClients getConnectedClients();

        ChatContext getCurrentSession();

        String getCurrentSessionName();
}

