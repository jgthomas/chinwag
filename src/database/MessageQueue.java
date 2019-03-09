package database;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
	private ConcurrentLinkedQueue insertionQueue;
	
	public MessageQueue() {
		this.insertionQueue = new ConcurrentLinkedQueue();
	}
	
	public void insertMessage(Message message) {
		
	}
}
