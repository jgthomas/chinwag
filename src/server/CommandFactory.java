package server;


import protocol.Action;

class CommandFactory {

        static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 UserChatSessions userChatSessions,
                 AllChatSessions allChatSessions,
                 ConnectedClients connectedClients)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case LOGIN:
                                return new LoginCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case SIGN_UP:
                                return new SignUpCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case LEAVE_CHAT:
                                return new LeaveChatCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case ADD_USER:
                                return new AddUserToChatCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case QUIT:
                                return new QuitCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        case GET_CHAT_SESSIONS:
                        case GET_MEMBERS:
                        case GET_LOGGED_IN:
                                return new InfoCommand(messageSender, userChatSessions, allChatSessions, connectedClients);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
