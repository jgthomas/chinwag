package server;


public class SwitchCommand extends Command {

        public SwitchCommand(ConnectionTracker connectionTracker) {
                super(connectionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getConnectionTracker().setCurrentSessionName(messageBox.getData());
        }
}
