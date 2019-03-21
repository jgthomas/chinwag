package server_command;

import protocol.Action;
import protocol.Data;
import protocol.DataFormatter;
import protocol.MessageBox;
import server.*;

import java.util.Collections;
import java.util.List;

class InfoSend extends Command {

    InfoSend(MessageSender messageSender,
             UserState userState,
             AllChatSessions allChatSessions,
             ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {
        String chatName = messageBox.get(Data.CHAT_NAME);
        MessageBox mb = null;

        switch (messageBox.getAction()) {
            case GET_CHAT_SESSIONS:
                mb = currentSessionsMessage();
                break;
            case GET_MEMBERS:
                if (chatName != null) {
                    mb = sessionMembersMessage(chatName);
                    mb.add(Data.CHAT_NAME, chatName);
                }
                break;
            case GET_LOGGED_IN:
                mb = loggedInUsersMessage();
                break;
            case GET_FRIENDS:
                mb = userFriendsMessage();
                break;
        }

        getMessageSender().sendMessage(mb);
    }

    private MessageBox currentSessionsMessage() {
        String sessionsString =
                buildStringMessage(getUserState().allUserChatSessions());
        MessageBox messageBox = new MessageBox(Action.GIVE_CHAT_SESSIONS);
        messageBox.add(Data.CHAT_SESSIONS, sessionsString);
        return messageBox;
    }

    private MessageBox sessionMembersMessage(String chatName) {
        String sessionMembersString =
                buildStringMessage(getAllChatSessions().getSession(chatName).allUserNames());
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

    private MessageBox userFriendsMessage() {
        String userFriendString =
                buildStringMessage(getUserState().getAllFriends());
        MessageBox messageBox = new MessageBox(Action.GIVE_FRIENDS);
        messageBox.add(Data.USER_FRIENDS, userFriendString);
        return messageBox;
    }

    private String buildStringMessage(List<String> s) {
        Collections.sort(s);
        return DataFormatter.listToString(s);
    }
}
