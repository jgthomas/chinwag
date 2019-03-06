package server;

import java.util.List;


class UsersCommand extends Command {

        UsersCommand(ConnectionTracker connectionTracker) {
                super(connectionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getMessageSender().sendMessage(new MessageBox(getNames()));
        }

        private String getNames() {
                List<String> names =
                        getConnectionTracker().getCurrentSession().allUserNames();
                return names.toString();
        }
}
