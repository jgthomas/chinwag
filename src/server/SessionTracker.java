package server;


interface SessionTracker extends Iterable<ChatSession> {

        void addSession(ChatSession chatContext);

        void removeSession(String sessionName);

        ChatSession getSession(String sessionName);

        void exitAll(MessageSender messageSender);

        ChatSession getCurrentSession();

        String getCurrentSessionName();
}

