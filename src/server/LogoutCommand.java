package server;

import protocol.MessageBox;

public class LogoutCommand extends Command {

	LogoutCommand(MessageSender messageSender, SessionTracker sessionTracker) {
		super(messageSender, sessionTracker);
	}

	@Override
	void execute(MessageBox messageBox) {
		
	}
	
}
