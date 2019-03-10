package server;


import protocol.Action;

class CommandFactory {

        static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 CurrentChatSessions currentChatSessions,
                 AllChatSessions allChatSessions,
                 ConnectedClients connectedClients)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case LOGIN:
                                return new LoginCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case SIGN_UP:
                                return new SignUpCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case LEAVE_CHAT:
                                return new LeaveChatCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case ADD_USER:
                                return new AddUserToChatCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        case QUIT:
                                return new QuitCommand(messageSender, currentChatSessions, allChatSessions, connectedClients);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
