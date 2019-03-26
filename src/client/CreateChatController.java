package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class CreateChatController {
	private Client client;
	private Stage stage;
	private LoginController controller;
	
	@FXML private TextField chatName;
	@FXML private Button createChat;
	@FXML private Button cancel;
	
	public CreateChatController(Client client, Stage stage,
			LoginController controller) {
		this.client = client;
		this.stage = stage;
		this.controller = controller;
	}
	
	public void cancel(ActionEvent e) {
		stage.close();
	}
	
	public void createChat(ActionEvent e) {
		MessageBox create = new MessageBox(Action.START_NEW_CHAT);
		create.add(Data.CHAT_NAME, chatName.getText());
		create.add(Data.USER_NAME, client.getUser().getUserName());
		TextArea newMessageSpace = new TextArea();
		newMessageSpace.setEditable(false);
		controller.getMessageSpaces().put(chatName.getText(), newMessageSpace);
		TreeItem<String> chatTreeItem = new TreeItem<>(chatName.getText());
		chatTreeItem.getChildren().add(new TreeItem<>(client.getUser().getUserName()));
		controller.getTreeViewRoot().getChildren().add(chatTreeItem);
		chatName.clear();
		client.sendMessage(create);
		stage.close();
	}
}
