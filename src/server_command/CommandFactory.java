package server_command;


import protocol.Action;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageSender;
import server.UserState;

public class CommandFactory {

        public static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 UserState userState,
                 AllChatSessions allChatSessions,
                 ConnectedClients connectedClients)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, userState, allChatSessions, connectedClients);
                        case LOGIN:
                                return new LoginCommand(messageSender, userState, allChatSessions, connectedClients);
                        case SIGN_UP:
                                return new SignUp(messageSender, userState, allChatSessions, connectedClients);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, userState, allChatSessions, connectedClients);
                        case LEAVE_CHAT:
                                return new LeaveChatCommand(messageSender, userState, allChatSessions, connectedClients);
                        case ADD_USER:
                                return new AddUserToChat(messageSender, userState, allChatSessions, connectedClients);
                        case ADD_FRIEND:
                                return new AddFriend(messageSender, userState, allChatSessions, connectedClients);
                        case QUIT:
                                return new QuitCommand(messageSender, userState, allChatSessions, connectedClients);
                        case INVITE:
                        	    return new InviteUserToChatCommand(messageSender, userState, allChatSessions, connectedClients);
                        case IMAGE:
                                return new ImageCommand(messageSender, userState, allChatSessions, connectedClients);
                        case GET_CHAT_HISTORY:
                        	return new GetChatHistoryCommand(messageSender, userState, allChatSessions, connectedClients);
                        case GET_CHAT_SESSIONS:
                        case GET_MEMBERS:
                        case GET_LOGGED_IN:
                        case GET_FRIENDS:
                                return new InfoCommand(messageSender, userState, allChatSessions, connectedClients);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
