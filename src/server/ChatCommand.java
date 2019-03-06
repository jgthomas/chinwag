package server;


class ChatCommand extends Command {

        ChatCommand(ConnectionTracker connectionTracker) {
                super(connectionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getMessageSender().postMessage(getCurrentChat(), messageBox);
        }

        private ChatContext getCurrentChat() {
                return getConnectionTracker().getCurrentSession();
        }
}
