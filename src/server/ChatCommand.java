package server;


import protocol.Data;
import protocol.MessageBox;


/**
 * CONTRACT
 *
 * Action: Action.CHAT
 *
 * Data Required:
 * Data.MESSAGE - the content to send to the chat
 *
 * TODO: specify the chat session to which the message should be posted
 *
 * Currently it is sent to the 'activeSession' as tracked on the server, we can
 * either have the client update this when active session changes, e.g. when GUI window
 * focus alters, and keep the current system. Or, we can have the client specify the
 * current chat session in the MessageBox directly, which would add a Data.CHAT_NAME
 * to the contract.
 *
 **/
class ChatCommand extends Command {

        ChatCommand(MessageSender messageSender,
                    UserChatSessions userChatSessions,
                    AllChatSessions allChatSessions,
                    ConnectedClients connectedClients)
        {
                super(messageSender, userChatSessions, allChatSessions, connectedClients);
        }

        /**
         * Sends a message to all users in a chat session.
         *
         * @param messageBox the command from the client to perform
         * */
        @Override
        public void execute(MessageBox messageBox) {
                String chatSessionName = messageBox.get(Data.CHAT_NAME);
                ChatSession chatSession = getUserChatSessions().getSession(chatSessionName);
                getMessageSender().postMessage(chatSession, messageBox);
        }
}
