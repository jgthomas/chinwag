package server;


interface SessionTracker extends Iterable<ChatContext> {

        void addSession(String sessionName, ChatContext chatContext);

        void removeSession(String sessionName);

        ChatContext getCurrentSession();

        ChatContext getSession(String sessionName);

        String getCurrentSessionName();

        void setCurrentSessionName(String sessionName);

        void exitAll();

        MessageSender getMessageSender();
}
