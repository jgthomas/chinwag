package server;

import protocol.Action;
import protocol.MessageBox;

import java.io.*;
import java.net.*;

/**
 * Deals with receiving data from the client.
 *
 * When data is received, in the form of a MessageBox object, it is passed to
 * the messageHandler.
 *
 * Runs until it receives the command to quit.
 *
 */
class Receiver implements MessageReceiver {
	private final Socket clientSocket;
	private final MessageHandler messageHandler;

	Receiver(Socket clientSocket, MessageHandler messageHandler) {
		this.clientSocket = clientSocket;
		this.messageHandler = messageHandler;
	}

	/**
	 * Listens for incoming messages from the client.
	 *
	 * Runs until it receives the instruction to quit.
	 *
	 */
	@Override
	public void listeningLoop() throws IOException{
		MessageBox messageBox = new MessageBox(Action.CHAT);

		// do not put this in resources because do not want to
		// auto-close after this method exits
		ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
		do {
			try {
				messageBox = (MessageBox) in.readObject();
				messageHandler.handle(messageBox);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} while (messageBox.getAction() != Action.QUIT);
	}
}
