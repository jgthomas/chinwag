package server;

import protocol.Data;
import protocol.DataFormatter;
import protocol.MessageBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoCommand extends Command {

    InfoCommand(MessageSender messageSender,
                UserChatSessions userChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {
        switch (messageBox.getAction()) {
            case LIST_CHAT_SESSIONS:
                String sessions = DataFormatter.listToString(currentSessions());
                break;
            case LIST_MEMBERS:
                String chatName = messageBox.get(Data.CHAT_NAME);
                String sessionMembers = DataFormatter.listToString(sessionMembers(chatName));
                break;
            case LIST_LOGGED_IN:
                String loggedInUsers = DataFormatter.listToString(loggedInUsers());
                break;
        }
    }

    private List<String> currentSessions() {
        List<String> sessions = getUserChatSessions().allUserChatSessions();
        Collections.sort(sessions);
        return sessions;
    }

    private List<String> loggedInUsers() {
        return new ArrayList<>();
    }

    private List<String> sessionMembers(String chatName) {
        return new ArrayList<>();
    }
}
