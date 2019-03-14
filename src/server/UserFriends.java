package server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserFriends {
	private final ConcurrentMap<String, MessageHandler> allFriends;
	private final ConcurrentMap<String, MessageHandler> activeFriends;
	
	public UserFriends() {
		allFriends = new ConcurrentHashMap<>();
		activeFriends = new ConcurrentHashMap<>();
	}
	
	public void addFriend(String friend) {
		
	}
	
	public void removeFriend(String friend) {
		
	}

	public ConcurrentMap<String, MessageHandler> getAllFriends() {
		return allFriends;
	}

	public ConcurrentMap<String, MessageHandler> getActiveFriends() {
		return activeFriends;
	}
	
	
}
