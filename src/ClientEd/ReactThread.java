package ClientEd;

import server.Action;
import server.MessageBox;

public class ReactThread implements Runnable {
	private Client client;
	private MessageBox message;
	
	ReactThread(Client client, MessageBox message) {
			this.client = client;
			this.message = message;
		}
		

	@Override
	public void run() {
		if (Action.isValidCommand(message.getAction().getToken())) { //for new version
			
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
