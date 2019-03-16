package server_command;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.*;

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
class SignUp extends Command {

    SignUp(MessageSender messageSender,
           UserState userState,
           AllChatSessions allChatSessions,
           ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
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
        	// generate salt
        	byte[] salt = Hasher.genSalt();
        	String saltString = Hasher.bytesToString(salt);
        	
        	// generate hash based on password and salt
        	String pwHash = Hasher.hashPassword(password, salt);
        	
        	// insert new user into database
            Database.insertNewUser(username, saltString, pwHash);

            // store a new user's membership to global chat
            //Database.addUserToChat("global", username);
            
            // inform user of successful signup
            MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
            mb.add(Data.MESSAGE, "Successfully signed up.");
            getMessageSender().sendMessage(mb);
        }
    }
}
