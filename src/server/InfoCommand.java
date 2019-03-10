package server;

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
                break;
            case LIST_LOGGED_IN:
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

    private List<String> sessionMembers() {
        return new ArrayList<>();
    }
}
