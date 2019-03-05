
import java.util.List;


public class UsersCommand extends Command {

        public UsersCommand(ConnectionTracker connectionTracker) {
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
