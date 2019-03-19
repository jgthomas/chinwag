package client;

import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.text.Text;
import javafx.util.Callback;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class LoginController {
	private Client client;
	private ClientMain clientMain;
	private MainController controller;
	private Scene scene;

	private ObservableList<String> friendsList;
	private TreeItem<String> treeViewRoot;
	private TreeView<String> chatTreeView;
	private HashMap<String, TextArea> messageSpaces;
	private Label loggedIn;
	@FXML private Button exit;
	@FXML private Button login;
	@FXML private Text signup;
	@FXML private Text noAccount;
	@FXML private Text username;
	@FXML private Text password;
	@FXML private TextField usernameField;
	@FXML private TextField passwordField;
	
	public LoginController(ClientMain clientMain) {
		client = new Client("localhost", 6000, this);
		this.clientMain = clientMain;
	}
	
	public void initialize() {		
		treeViewRoot = new TreeItem<String>();
		friendsList = FXCollections.observableArrayList();
		messageSpaces = new HashMap<String, TextArea>();
	}

	@FXML
	public void exit(ActionEvent e) {
		MessageBox logout = new MessageBox(Action.QUIT);
		logout.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(logout);
		System.exit(1);
	}
	
	@FXML 
	public void login(ActionEvent e) {
		MessageBox login = new MessageBox(Action.LOGIN);
		login.add(Data.USER_NAME, usernameField.getText());
		client.getUser().setUserName(usernameField.getText());
		usernameField.clear();
		login.add(Data.PASSWORD, passwordField.getText() + "");
		passwordField.clear();
		client.sendMessage(login);
	}
	
	@FXML
	public void signup(MouseEvent e) {
		
	}
	
	public void login() throws IOException {
		//loggedInAs = new Text("Logged in as " + loggedInName);
		loggedIn = new Label();
		loggedIn.setText("Logged in as " + client.getUser().getUserName());
		controller = new MainController(clientMain, client, 
				treeViewRoot, chatTreeView, messageSpaces, loggedIn, friendsList,
				scene, this);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		
		scene = new Scene(root);
		clientMain.getStage().setTitle("MessengerClient");
		clientMain.getStage().setScene(scene);
		clientMain.getStage().show();
		//drawMainScreen(messageSpaces.get(chatTreeView.getSelectionModel().getSelectedItem().getValue()));
		//messageSpaces.get("global").appendText("Login successful!" + "\n");
	}
	
	public void refuseLogin() {
		//drawFailedLogonScreen();
	}
	
	public MainController getMainController() {
		return controller;
	}
	
	public TreeView<String> getChatTreeView() {
		return chatTreeView;
	}
	
	public TreeItem<String> getTreeViewRoot() {
		return treeViewRoot;
	}
	
	public ObservableList<String> getFriendsList(){
		return friendsList;
	}
	
	public HashMap<String, TextArea> getMessageSpaces() {
		return messageSpaces;
	}
	
	public void addSessionSpace(String session) {
		TextArea newSpace = new TextArea();
//		newSpace.setOnMouseReleased(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent e) {
//				requestInputFocus();
//			}	
//		});
		newSpace.setEditable(false);
		messageSpaces.put(session, newSpace);
	}
	
}
