package server;


public abstract class Command {
        private final ConnectionTracker connectionTracker;

        Command(ConnectionTracker connectionTracker) {
                this.connectionTracker = connectionTracker;
        }

        MessageSender getMessageSender() {
                return connectionTracker.getMessageSender();
        }

        ConnectionTracker getConnectionTracker() {
                return connectionTracker;
        }

        public abstract void execute(MessageBox messageBox);
}
