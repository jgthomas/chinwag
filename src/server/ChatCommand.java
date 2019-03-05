package server;


public class ChatCommand extends Command {

        public ChatCommand(ConnectionTracker connectionTracker) {
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
