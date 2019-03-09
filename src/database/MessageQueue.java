package database;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
	private ConcurrentLinkedQueue insertionQueue;
	private ConcurrentLinkedQueue retrievalQueue;
	
	public MessageQueue() {
		
	}
}
