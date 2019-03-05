

public interface MessageSender {

        void postMessage(ChatContext chatContext, MessageBox messageBox);

        void sendMessage(MessageBox messageBox);

        void closeSender();

        User getUser();
}
