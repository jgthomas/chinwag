package server;


import database.Database;

class LoginCommand extends Command {

	LoginCommand(ConnectionTracker connectionTracker) {
		super(connectionTracker);
	}

	@Override
	public void execute(MessageBox2 messageBox) {
		String username = messageBox.get(Data.USERNAME);
		String password = messageBox.get(Data.PASSWORD);
		if (Database.isValidUser(username, password)){
			User newUser = new User(null, username)
		}
//		setUserName(messageBox.get(Data.USERNAME));
//		registerSender();
//		getMessageSender().sendMessage(new MessageBox(Action.CHAT, "Hello", "Hello!"));
	}

	private void setUserName(String username) {
		getMessageSender().getUser().setUsername(username);
	}

	private void registerSender() {
		getConnectionTracker().getSession("global").addUser(getMessageSender());
	}
}
