package client;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class InvitationController {
	private Client client;
	private Stage stage;
	private TreeItem<String> treeViewRoot;
	private HashMap<String, TextArea> messageSpaces;
	private String inviteName;
	private String inviter;
	
	@FXML private Text invite;
	@FXML private Button accept;
	@FXML private Button decline;
	
	public InvitationController(Client client, Stage stage, TreeItem<String> treeViewRoot,
			HashMap<String, TextArea> messageSpaces, String inviteName, String inviter) {
		this.client = client;
		this.stage = stage;
		this.treeViewRoot = treeViewRoot;
		this.messageSpaces = messageSpaces;
		this.inviteName = inviteName;
		this.inviter = inviter;
	}
	
	public void initialize() {
		invite.setText(inviter + " invited you to chat!");
	}
	
	public void accept() {
		MessageBox accept = new MessageBox(Action.ADD_USER);
		accept.add(Data.USER_NAME, client.getUser().getUserName());
		accept.add(Data.CHAT_NAME, inviteName);
		client.sendMessage(accept);
		treeViewRoot.getChildren().add(new TreeItem<String>(inviteName));
		stage.close();
		messageSpaces.put(inviteName, new TextArea());
	}
	
	public void decline() {
		stage.close();
	}
}
