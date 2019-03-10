package server;

import protocol.Action;
import protocol.Data;
import protocol.DataFormatter;
import protocol.MessageBox;

import java.util.Collections;
import java.util.List;

class InfoCommand extends Command {

    InfoCommand(MessageSender messageSender,
                UserChatSessions userChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    @Override
    void execute(MessageBox messageBox) {
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
        String sessionsString =
                buildStringMessage(getUserChatSessions().allUserChatSessions());
        MessageBox messageBox = new MessageBox(Action.GIVE_CHAT_SESSIONS);
        messageBox.add(Data.CHAT_SESSIONS, sessionsString);
        return messageBox;
    }

    private MessageBox sessionMembersMessage(String chatName) {
        String sessionMembersString =
                buildStringMessage(getUserChatSessions().getSession(chatName).allUserNames());
        MessageBox messageBox = new MessageBox(Action.GIVE_MEMBERS);
        messageBox.add(Data.CHAT_NAME, chatName);
        messageBox.add(Data.CHAT_MEMBERS, sessionMembersString);
        return messageBox;
    }

    private MessageBox loggedInUsersMessage() {
        String loggedInString =
                buildStringMessage(getConnectedClients().allLoggedInUsers());
        MessageBox messageBox = new MessageBox(Action.GIVE_LOGGED_IN);
        messageBox.add(Data.LOGGED_IN_MEMBERS, loggedInString);
        return messageBox;
    }

    private String buildStringMessage(List<String> s) {
        Collections.sort(s);
        return DataFormatter.listToString(s);
    }
}
