package server;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

/**
 * CONTRACT
 *
 * Action: Action.SIGN_UP
 *
 * Data Required:
 * Data.USER_NAME
 * Data.PASSWORD
 *
 **/
class SignUpCommand extends Command {

    SignUpCommand(MessageSender messageSender,
                  UserChatSessions userChatSessions,
                  AllChatSessions allChatSessions,
                  ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    /**
     * Creates a new account
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox){
        String username = messageBox.get(Data.USER_NAME);
        String password = messageBox.get(Data.PASSWORD);
        if (Database.userExists(username)){
            MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
            mb.add(Data.MESSAGE, "The username already exists.");
            getMessageSender().sendMessage(mb);
        } else {
            Database.insertNewUser(username, password);
            MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
            mb.add(Data.MESSAGE, "Successfully signed up.");
            getMessageSender().sendMessage(mb);
        }
    }
}
