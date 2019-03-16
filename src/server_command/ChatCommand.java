package server_command;


import database.MessageQueue;
import protocol.Data;
import protocol.MessageBox;
import server.*;


/**
 * CONTRACT
 *
 * Action: Action.CHAT
 *
 * Data Required:
 * Data.MESSAGE - the content to send to the chat
 * Data.CHAT_NAME - the chat to which the message should be sent
 *
 **/
class ChatCommand extends Command {

        ChatCommand(MessageSender messageSender,
                    UserState userState,
                    AllChatSessions allChatSessions,
                    ConnectedClients connectedClients)
        {
                super(messageSender, userState, allChatSessions, connectedClients);
        }

        /**
         * Sends a message to all users in a chat session.
         *
         * @param messageBox the command from the client to perform
         * */
        @Override
        public void execute(MessageBox messageBox) {
                MessageQueue.addToQueue(messageBox);

                String chatName = messageBox.get(Data.CHAT_NAME);
                ChatSession chatSession = getAllChatSessions().getSession(chatName);
                getMessageSender().postMessage(chatSession, messageBox);

        }
}
