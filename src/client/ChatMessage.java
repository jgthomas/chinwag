package client;

/**
 * Represents an individual message in a chat.
 * 
 * Does not contain a ChatSession field because when the client requests a chat history, it will
 * be specified which ChatSession this is for. WHen the server responds, it will specify which 
 * ChatSession its history is for once in a Data field. Therefore, there is no need to have a 4th
 * field
 * 
 * @author ed
 *
 */

public class ChatMessage {
	private String timeSent;
	private String sender;
	private String messageBody;
	

}
