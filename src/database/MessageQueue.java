package database;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;

import protocol.Data;
import protocol.MessageBox;

public class MessageQueue implements Runnable {
	private static Queue<Message> insertionQueue =
			new LinkedList<>();

	public static void addToQueue(MessageBox messageBox) {
		String chatname = messageBox.get(Data.CHAT_NAME);
		String sender = messageBox.get(Data.USER_NAME);
		String content = messageBox.get(Data.MESSAGE);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Message message = new Message(chatname, sender, content, timestamp);
		insertionQueue.add(message);
	}

	@Override
	public void run() {
		while (true) {
			if(!insertionQueue.isEmpty()) {
				Database.insertMessage(insertionQueue.remove());
			}
		}
	}


}
