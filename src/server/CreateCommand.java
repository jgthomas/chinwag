package server;


class CreateCommand extends Command {

        CreateCommand(ConnectionTracker connectionTracker) {
                super(connectionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                String newChatName = messageBox.getData();
                ChatContext newChat = new ChatSession(newChatName);
                newChat.addUser(getMessageSender());
                getConnectionTracker().addSession(newChatName, newChat);
        }
}
