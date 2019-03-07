package ClientEd;

import protocol.Action;

public class ActionThread implements Runnable {
	private Client client;
	private Action action;

	ActionThread(Client client, Action action) {
		this.client = client;
		this.action = action;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
