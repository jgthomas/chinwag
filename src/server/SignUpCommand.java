package server;

import database.Database;

public class SignUpCommand extends Command{

    public SignUpCommand(MessageSender messageSender, SessionTracker sessionTracker){
        super(messageSender, sessionTracker);
    }

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
