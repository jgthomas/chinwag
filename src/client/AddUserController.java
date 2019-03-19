package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class AddUserController {
	private Stage stage;
	private Client client;
	private TreeView<String> chatTreeView;
	
	@FXML private TextField username;
	@FXML private Button addUser;
	@FXML private Button cancel;
	
	public AddUserController(Client client, Stage stage, TreeView<String> chatTreeView) {
		this.client = client;
		this.stage = stage;
		this.chatTreeView = chatTreeView;
	}
	
	public void add(ActionEvent e) {
		MessageBox invite = new MessageBox(Action.INVITE);
		invite.add(Data.USER_NAME, username.getText());
		username.clear();
		invite.add(Data.CHAT_NAME, chatTreeView
								   .getSelectionModel()
								   .getSelectedItem()
								   .getValue());
		client.sendMessage(invite);
	}
	
	public void cancel(ActionEvent e) {
		stage.close();
	}
}
