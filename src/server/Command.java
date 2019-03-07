package server;

abstract class Command {
        private final SessionTracker sessionTracker;

        Command(SessionTracker sessionTracker) {
                this.sessionTracker = sessionTracker;
        }

        MessageSender getMessageSender() {
                return sessionTracker.getMessageSender();
        }

        SessionTracker getSessionTracker() {
                return sessionTracker;
        }

        abstract void execute(MessageBox messageBox);
}
