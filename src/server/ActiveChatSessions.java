package server;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A record of all currently active chats
 *
 * When a chat session is created, it is added here.
 *
1 * */
public class ActiveChatSessions {
    private final ConcurrentMap<String, ChatSession> activeSessions;

    ActiveChatSessions() {
        activeSessions = new ConcurrentHashMap<>();
    }

    /**
     * Temporary constructor to take the global chat on creation.
     *
     * When we remove the global chat we can remove this as well.
     *
     * @param global the global chat session
     * */
    ActiveChatSessions(ChatSession global) {
        activeSessions = new ConcurrentHashMap<>();
        activeSessions.put(global.getChatName(), global);
    }

    /**
     * Adds a new chat session to the server master record.
     *
     * @param sessionID the unique identifier of the chat session
     * @param chatSession the chat session object
     * */
    void addSession(String sessionID, ChatSession chatSession) {
        activeSessions.put(sessionID, chatSession);
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
}
