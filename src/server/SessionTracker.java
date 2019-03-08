package server;


interface SessionTracker extends Iterable<ChatContext> {

        void addSession(ChatContext chatContext);

        void removeSession(String sessionName);

        ChatContext getCurrentSession();

        ChatContext getSession(String sessionName);

        String getCurrentSessionName();

        void setCurrentSessionName(String sessionName);

        void exitAll();

        void addUserToChat(String chatName, String userName);

        ConnectedClients getConnectedClients();
}
