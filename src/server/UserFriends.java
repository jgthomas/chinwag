package server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserFriends {
	private final ConcurrentMap<String, MessageHandler> allFriends;
	
	public UserFriends() {
		allFriends = new ConcurrentHashMap<>();
	}
	
	public void addFriend(String friend) {
		
	}
	
	public void removeFriend(String friend) {
		
	}

	public ConcurrentMap<String, MessageHandler> getAllFriends() {
		return allFriends;
	}

}
