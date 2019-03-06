package server;

import java.util.EnumMap;

public class MessageBox2 implements DataTransfer {
    private final Action action;
    private final EnumMap<Data, String> messageData;

    public MessageBox2(Action action) {
        this.action = action;
        this.messageData = new EnumMap<Data, String>(Data.class);
    }

    @Override
    public void add(Data data, String s) {
        messageData.put(data, s);
    }

    @Override
    public String get(Data data) {
        return messageData.get(data);
    }

}
