package client;

import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class MainController {
	private Client client;
	private Stage stage;
	private Scene scene;
	private LoginController controller;
	private String inviter;
	
	private String currentSpace;
	private String inviteName;
	private ObservableList<String> friendsList;
	private TreeItem<String> treeViewRoot;
	private HashMap<String, TextArea> messageSpaces;
	@FXML private Button send;
	@FXML private TextField input;
	@FXML private TreeView<String> chatTreeView;
	@FXML private ListView<String> friendsListView;
	@FXML private Label loggedIn;
	@FXML private Button exit;
	@FXML private TextArea messageSpace;
	@FXML private Button leaveChat;
	@FXML private Button addUser;
	@FXML private Button logout;
	@FXML private Button addFriend;
	@FXML private Button createChat;
	
	public MainController(Stage stage, Client client, TreeItem<String> treeViewRoot,
			TreeView<String> chatTreeView, HashMap<String, TextArea> messageSpaces,
			Label loggedIn, ObservableList<String> friendsList, Scene scene, LoginController controller) {
		this.client = client;
		this.stage = stage;
		this.scene = scene;
		this.controller = controller;
		
		this.treeViewRoot = treeViewRoot;
		this.messageSpaces = messageSpaces;
		this.friendsList = friendsList;
	}
	
	public void initialize() {
		loggedIn.setText("Logged in as " + client.getUser().getUserName());
		
		messageSpace.setEditable(false);
		
		chatTreeView.setRoot(treeViewRoot);
		chatTreeView.setShowRoot(false);
		chatTreeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>(){
			@Override
			public TreeCell<String> call(TreeView<String> tv){
				return new OnlineIndicatorTreeCell(client);
			}
		});
		//messageSpace.setText(messageSpaces.get(chatTreeView.getSelectionModel().getSelectedItem().getValue()).getText());
		//currentSpace = chatTreeView.getSelectionModel().getSelectedItem().getValue();

		friendsListView.setItems(friendsList);
		friendsListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){
			@Override
			public ListCell<String> call(ListView<String> lv){
				return new OnlineIndicatorListCell(client);
			}
		});
	}
	
	@FXML
	public void sendMessage(ActionEvent e) {
		messageSpace.appendText("You>>> " + input.getText() + "\n");
		MessageBox message = new MessageBox(Action.CHAT);
		message.add(Data.CHAT_NAME, chatTreeView.getSelectionModel().getSelectedItem().getValue());
		message.add(Data.USER_NAME, client.getUser().getUserName());
		message.add(Data.MESSAGE, input.getText());
		input.clear();
		client.sendMessage(message);
	}
	
	@FXML
	public void pressTreeView(MouseEvent e) {
		if(chatTreeView.getSelectionModel().getSelectedItem().getParent().getParent() == null) {
			String space = chatTreeView.getSelectionModel().getSelectedItem().getValue();
			if(!(currentSpace == null)) {
				messageSpaces.get(currentSpace)
							 .setText(messageSpace.getText());
			}
			currentSpace = chatTreeView.getSelectionModel().getSelectedItem().getValue();
			messageSpace.setText(messageSpaces.get(chatTreeView.getSelectionModel().getSelectedItem().getValue()).getText());
			for (TreeItem<String> item : treeViewRoot.getChildren()) {
				item.setExpanded(false);
			}
			chatTreeView.getSelectionModel().getSelectedItem().setExpanded(true);
			input.requestFocus();
		} //else
		//	chatTreeView.getSelectionModel().select(chatTreeView.getSelectionModel().getSelectedItem().getParent());
		
	}
	
	@FXML
	public void pressListView(MouseEvent e) {
		
	}
	
	@FXML
	public void exit(ActionEvent e) {
		MessageBox logout = new MessageBox(Action.QUIT);
		logout.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(logout);
		System.exit(1);
	}
	
	@FXML
	public void logout(ActionEvent e) {
		MessageBox logout = new MessageBox(Action.QUIT);
		logout.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(logout);
		//drawLogonScreen();
	}
	
	@FXML
	public void addUser(ActionEvent e) {
		Stage stage = new Stage();
		AddUserController controller = new AddUserController(client, stage, chatTreeView);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			scene = new Scene(root);
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void leaveChat(ActionEvent e) {
		MessageBox leave = new MessageBox(Action.LEAVE_CHAT);
		TreeItem<String> toLeave = chatTreeView.getSelectionModel().getSelectedItem();
		leave.add(Data.CHAT_NAME, toLeave.getValue());
		client.sendMessage(leave);
		treeViewRoot.getChildren().remove(toLeave);
	}
	
	@FXML
	public void createChat(ActionEvent e) {
		Stage stage = new Stage();
		CreateChatController controller = new CreateChatController(client, stage, this.controller);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateChat.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			scene = new Scene(root);
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void addFriend(ActionEvent e) {
		Stage stage = new Stage();
		AddFriendController controller = new AddFriendController(client, friendsList, stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFriend.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			scene = new Scene(root);
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void displayMessage(MessageBox mb) {
		if(mb.get(Data.CHAT_NAME) == null) {
			return;
		}
		messageSpaces.get(mb.get(Data.CHAT_NAME))
					 .appendText(mb.get(Data.USER_NAME) + 
							 ">>> " + mb.get(Data.MESSAGE) + "\n");
		if(chatTreeView.getSelectionModel().getSelectedItem().getValue().equals(mb.get(Data.CHAT_NAME))) {
			messageSpace.appendText(mb.get(Data.USER_NAME) + 
							 ">>> " + mb.get(Data.MESSAGE) + "\n");
		}
	}
	
	public void drawChatCreationRefusal() {
		Group root = new Group();
		VBox v = new VBox();
		Text warning = new Text("Cannot create chat with that name"); //tell them why?
		v.getChildren().add(warning);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		//chatCreationRefusalStage.setScene(scene);
		//chatCreationRefusalStage.show();
	}
	
	public void drawInviteScreen(MessageBox mb) {
		Stage stage = new Stage();
		InvitationController controller = new InvitationController(client, stage, 
				treeViewRoot, messageSpaces, inviteName, inviter);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Invitation.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			scene = new Scene(root);
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
	
	public void requestInputFocus() {
		input.requestFocus();
	}
	
	public void updateFriendsListView() {
		friendsListView.refresh();
	}
	
	public TreeView<String> getChatTreeView() {
		return chatTreeView;
	}
	
	public void selectFirstChat() {
		chatTreeView.getSelectionModel().selectFirst();
		chatTreeView.getSelectionModel().getSelectedItem().setExpanded(true);
		messageSpace.setText(messageSpaces.get(chatTreeView.getSelectionModel().getSelectedItem().getValue()).getText());
		currentSpace = chatTreeView.getSelectionModel().getSelectedItem().getValue();
	}
}
