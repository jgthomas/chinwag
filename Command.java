

public abstract class Command {
        private final ConnectionTracker connectionTracker;

        public Command(ConnectionTracker connectionTracker) {
                this.connectionTracker = connectionTracker;
        }

        public MessageSender getMessageSender() {
                return connectionTracker.getMessageSender();
        }

        public ConnectionTracker getConnectionTracker() {
                return connectionTracker;
        }

        public abstract void execute(MessageBox messageBox);
}
