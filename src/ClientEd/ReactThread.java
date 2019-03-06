package ClientEd;

import server.Action;
import server.MessageBox;

public class ReactThread implements Runnable {
	private ClientDraft client;
	private MessageBox message;
	
	ReactThread(ClientDraft client, MessageBox message) {
			this.client = client;
			this.message = message;
		}
		
	}

	@Override
	public void run() {
		if (!Action.isValidCommand(message.getCommand().getToken())) {
			//Throw exception?
			//Do nothing?
		
	}

}
