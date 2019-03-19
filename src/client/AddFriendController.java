package client;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class AddFriendController {
	private Client client;
	private ObservableList<String> friendsList;
	private Stage stage;
	
	@FXML private TextField username;
	@FXML private Button cancel;
	@FXML private Button addFreind;
	
	public AddFriendController(Client client, ObservableList<String> friendsList,
			Stage stage) {
		this.stage = stage;
		this.client = client;
		this.friendsList = friendsList;
	}
	
	public void cancel(ActionEvent e) {
		stage.close();
	}
	
	public void addFriend(ActionEvent e) {
		MessageBox add = new MessageBox(Action.ADD_FRIEND);
		add.add(Data.USER_NAME, username.getText());
		friendsList.add(username.getText());
		username.clear();
		client.sendMessage(add);
	}
}
