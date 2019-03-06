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
		

	@Override
	public void run() {
		if (Action.isValidCommand(message.getCommand().getToken())) { //change back to value
			
		}
		else {
			throw new IllegalArgumentException("Invalid command");
		}
	}

	void loginRefusedResponse() {
		System.out.println("Login credentials refused");
		
	}
	
	/**
	 * Can add "logged in as ..."
	 * Last logged in at ...
	 * 
	 */
	void loginAcceptedResponse() {
		System.out.println("Login successful");
		//Allow client to do things
	}
	
}
