package database;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {
	private String chatname, sender, content;
	private Timestamp timestamp;
	
	public Message(String chatname, String sender, String content, Timestamp timestamp) {
		this.chatname = chatname;
		this.sender = sender;
		this.content = content;
		this.timestamp = timestamp;
	}

	public String getChatname() {
		return chatname;
	}

	public String getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public boolean equals(Message message){
		if (this.chatname.equals(message.getChatname()) && this.sender.equals(message.getSender()) &&
		this.content.equals(message.getContent()) && this.timestamp.equals(message.getTimestamp()))
			return true;
		else
			return false;
	}
	
	
}
