package server;


public class SessionCommand extends Command {

        public SessionCommand(ConnectionTracker connectionTracker) {
                super(connectionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getMessageSender().sendMessage(currentSessionMessage());
        }

        private MessageBox currentSessionMessage() {
                return new MessageBox(getConnectionTracker().getCurrentSessionName());
        }
}
