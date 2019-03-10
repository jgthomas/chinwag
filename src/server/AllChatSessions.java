package server;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A record of all currently active chats
 *
 * When a chat session is created, it is added here.
 *
1 * */
class AllChatSessions {
    private final ConcurrentMap<String, ChatSession> activeSessions;

    AllChatSessions() {
        activeSessions = new ConcurrentHashMap<>();
    }

    /**
     * Temporary constructor to take the global chat on creation.
     *
     * When we remove the global chat we can remove this as well.
     *
     * @param global the global chat session
     * */
    AllChatSessions(ChatSession global) {
        activeSessions = new ConcurrentHashMap<>();
        activeSessions.put(global.getChatName(), global);
    }

    /**
     * Adds a new chat session to the server master record.
     *
     * @param chatSession the chat session object
     * */
    void addSession(ChatSession chatSession) {
        activeSessions.put(chatSession.getChatName(), chatSession);
    }

    /**
     * Removes the chat session from the master record
     *
     * @param chatName the name off the chat session to remove
     * */
    void removeSession(String chatName) {
        activeSessions.remove(chatName);
    }

    /**
     * Gets the chat session from the master record.
     *
     * @param sessionID the unique identifier of the chat session
     * @return the chat session object, null if not found
     * */
    ChatSession getSession(String sessionID) {
        return activeSessions.getOrDefault(sessionID, null);
    }

    boolean sessionExists(String chatname) {
        return activeSessions.containsKey(chatname);
    }
}
