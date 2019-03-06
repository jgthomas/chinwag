package server;

import java.io.Serializable;
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
 * mb.get(Data.USER_NAME)
 * mb.get(Data.PASSWORD)
 *
 * */
public class MessageBox2 implements DataTransfer, Serializable {
    private final Action action;
    private final EnumMap<Data, String> messageData;

    /**
     * @param action a member of the Action enum,
     *               indicating the action to perform,
     *               chat, quit, login etc
     *
     * */
    public MessageBox2(Action action) {
        this.action = action;
        this.messageData = new EnumMap<Data, String>(Data.class);
    }

    /**
     * @param data a member of the Data enum
     * @param s a string, could be a message,
     *          username or password, etc.
     *
     **/
    @Override
    public void add(Data data, String s) {
        messageData.put(data, s);
    }

    /**
     * @param data a member of the Data enum
     * @return the string associated with that member in the map
     */
    @Override
    public String get(Data data) {
        return messageData.get(data);
    }

}
