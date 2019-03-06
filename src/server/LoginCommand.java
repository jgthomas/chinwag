package server;


class LoginCommand extends Command {

	LoginCommand(ConnectionTracker connectionTracker) {
		super(connectionTracker);
	}

	@Override
	public void execute(MessageBox messageBox) {
		setUserName(messageBox.get(Data.USER_NAME));
		registerSender();
		MessageBox mb = new MessageBox(Action.CHAT);
		mb.add(Data.MESSAGE, "hello");
		getMessageSender().sendMessage(mb);
		//getMessageSender().sendMessage(new MessageBox(Action.CHAT, "Hello", "Hello!"));
	}

	private void setUserName(String username) {
		getMessageSender().getUser().setName(username);
	}

	private void registerSender() {
		getConnectionTracker().getSession("global").addUser(getMessageSender());
	}
}
