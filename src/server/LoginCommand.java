package server;


import database.Database;

class LoginCommand extends Command {

	LoginCommand(ConnectionTracker connectionTracker) {
		super(connectionTracker);
	}

	@Override
	public void execute(MessageBox messageBox) {
		String username = messageBox.get(Data.USERNAME);
		String password = messageBox.get(Data.PASSWORD);
		if (Database.isValidUser(username, password)){
			MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
			mb.add(Data.MESSAGE, "Login successful!");
			getMessageSender().sendMessage(mb);
			setUserName(messageBox.get(Data.USERNAME));
			registerSender();
			//getMessageSender().sendMessage(new MessageBox(Action.CHAT, "Hello", "Hello!"));
		}

	}

	private void setUserName(String name) {
		getMessageSender().getUser().setName(name);
	}

	private void registerSender() {
		getConnectionTracker().getSession("global").addUser(getMessageSender());
	}
}
