package server;

import protocol.Action;
import protocol.Data;
import protocol.DataFormatter;
import protocol.MessageBox;

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
        String chatName = messageBox.get(Data.CHAT_NAME);
        MessageBox mb = null;

        switch (messageBox.getAction()) {
            case LIST_CHAT_SESSIONS:
                mb = currentSessionsMessage();
                break;
            case LIST_MEMBERS:
                mb = sessionMembersMessage(chatName);
                break;
            case LIST_LOGGED_IN:
                mb = loggedInUsersMessage();
                break;
        }

        ChatSession chatSession = getUserChatSessions().getSession(chatName);
        getMessageSender().postMessage(chatSession, mb);
    }

    private MessageBox currentSessionsMessage() {
        List<String> sessions = getUserChatSessions().allUserChatSessions();
        Collections.sort(sessions);
        String sessionsString = DataFormatter.listToString(sessions);
        MessageBox messageBox = new MessageBox(Action.GIVE_CHAT_SESSIONS);
        messageBox.add(Data.CHAT_SESSIONS, sessionsString);
        return messageBox;
    }

    private MessageBox sessionMembersMessage(String chatName) {
        List<String> members = getUserChatSessions().getSession(chatName).allUserNames();
        Collections.sort(members);
        String sessionMembersString = DataFormatter.listToString(members);
        MessageBox messageBox = new MessageBox(Action.GIVE_MEMBERS);
        messageBox.add(Data.CHAT_NAME, chatName);
        messageBox.add(Data.CHAT_MEMBERS, sessionMembersString);
        return messageBox;
    }

    private MessageBox loggedInUsersMessage() {
        List<String> loggedIn = getConnectedClients().allLoggedInUsers();
        Collections.sort(loggedIn);
        String loggedInString = DataFormatter.listToString(loggedIn);
        MessageBox messageBox = new MessageBox(Action.GIVE_LOGGED_IN);
        messageBox.add(Data.LOGGED_IN_MEMBERS, loggedInString);
        return messageBox;
    }
}
