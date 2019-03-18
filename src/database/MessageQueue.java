package database;

import java.sql.Timestamp;
import java.util.concurrent.*;

import protocol.Data;
import protocol.MessageBox;

public class MessageQueue implements Runnable {
	private static BlockingQueue<Message> insertionQueue = new LinkedBlockingQueue<>();

	public static void addToQueue(MessageBox messageBox) {
		String chatname = messageBox.get(Data.CHAT_NAME);
		String sender = messageBox.get(Data.USER_NAME);
		String content = messageBox.get(Data.MESSAGE);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Message message = new Message(chatname, sender, content, timestamp);
		try {
			insertionQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			Message message;
			try {
				message = insertionQueue.take();
				Database.insertMessage(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
