package protocol;


import database.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;


/**
 * New MessageBox
 *
 * Build the object with an initial Action, then
 * add fields as required to the map.
 *
 * Map has Data enum values as keys and strings as values.
 *
 * Samples use:
 *
 * MessageBox mb = new MessageBox2(Action.LOGIN)
 * mb.add(Data.USER_NAME, "mark")
 * mb.add(Data.PASSWORD, "letmein")
 *
 * Other end:
 *
 * String userName = mb.get(Data.USER_NAME)
 * String password = mb.get(Data.PASSWORD)
 *
 * */
public class MessageBox implements Serializable {
    private final Action action;
    private final EnumMap<Data, String> messageData;
    private ArrayList<Message> messageHistory;

    /**
     * @param action a member of the Action enum,
     *               indicating the action to perform,
     *               chat, quit, login etc
     *
     * */
    public MessageBox(Action action) {
        this.action = action;
        this.messageData = new EnumMap<>(Data.class);
        messageHistory = new ArrayList<>();
    }

    public Action getAction() {
        return action;
    }

    /**
     * Adds a list of message objects for transmitting message
     * history of a chat
     *
     * @param messageHistory a list of message history objects
     * */
    public void addMessageHistory(ArrayList<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    /**
     * Gets the message history list
     *
     * @return a list of message history objects
     * */
    public ArrayList<Message> getMessageHistory() {
        return messageHistory;
    }

    /**
     * @param data a member of the Data enum
     * @param s a string, could be a message,
     *          username or password, etc.
     *
     **/
    public void add(Data data, String s) {
        messageData.put(data, s);
    }

    /**
     * @param data a member of the Data enum
     * @return the string associated with that member in the map
     *         if member is not included, method returns null
     */
    public String get(Data data) {
        return messageData.getOrDefault(data, null);
    }

}
