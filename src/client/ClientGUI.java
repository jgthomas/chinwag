package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class ClientGUI extends Application {
	private Button login;
	private Button exit;
	private Button send;
	private Button createAccount;
	private Button create;
	private Button back;
	private Button logout;
	private Button invite;
	private Button add;
	private Button requestCreateChat;
	private Button createChat;
	private Button cancel;
	private Button accept;
	private Button decline;
	private TextField chatName;
	private TextField username;
	private PasswordField password;
	private TextField input;
	private Text loggedInAs;
	private TextArea messageSpace;
	private HashMap<String, TextArea> messageSpaces;
	private ListView<String> chatListView;
	private ObservableList<String> observableChatList;
	private VBox v;
	private HBox h;
	private HBox hMain;
	private Stage stage;
	private Stage createStage;
	private Stage addStage;
	private Stage inviteStage;
	private String loggedInName;
	private String inviteName;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		createStage = new Stage();
		addStage = new Stage();
		inviteStage = new Stage();
		
		observableChatList = FXCollections.observableArrayList();
		observableChatList.add("global");
		
		chatListView = new ListView<String>();
		chatListView.setItems(observableChatList);
		chatListView.getSelectionModel().selectFirst();
		
		messageSpace = new TextArea();
		messageSpace.setEditable(false);
		
		messageSpaces = new HashMap<String, TextArea>();
		
		chatListView.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String space = chatListView.getSelectionModel().getSelectedItem();
				drawMainScreen(messageSpaces.get(space));
			}
		});
		
		username = new TextField("Enter username...");
		
		password = new PasswordField(); //Text entered shows up as asterisks in GUI
		password.setPromptText("Enter password");
		
		input = new TextField();
		input.setPromptText("Enter message..."); //Text disappears after user begins typing
		
		input.setPrefWidth(500);
		input.setMinWidth(500);
		input.setMaxWidth(500);
		
		login = new Button("Login");
		login.setDefaultButton(true);
		
		Client client = new Client("localhost", 6000, this);
		
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MessageBox login = new MessageBox(Action.LOGIN);
				login.add(Data.USER_NAME, username.getText());
				setLoggedInName(username.getText());
				username.clear();
				login.add(Data.PASSWORD, password.getText() + "");
				password.clear();
				client.sendMessage(login);
			}
		});
		
		exit = new Button("Exit");
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MessageBox logout = new MessageBox(Action.QUIT);
				logout.add(Data.USER_NAME, loggedInName);
				client.sendMessage(logout);
				System.exit(1);
			}
		});
		
		send = new Button("Send");
		send.setDefaultButton(true);
		
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				messageSpaces.get(chatListView.getSelectionModel().getSelectedItem())
							 .appendText("You>>> " + input.getText() + "\n");
				MessageBox message = new MessageBox(Action.CHAT);
				message.add(Data.CHAT_NAME, chatListView
											.getSelectionModel()
											.getSelectedItem());
				message.add(Data.USER_NAME, loggedInName);
				message.add(Data.MESSAGE, input.getText());
				input.clear();
				client.sendMessage(message);
			}
		});
		
		back = new Button("Back");
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				drawLogonScreen();
			}
		});
		
		createAccount = new Button("Create Account");
		
		createAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Group root = new Group();
				Scene scene = new Scene(root);
				v.getChildren().clear();
				h.getChildren().clear();
				v.getChildren().add(username);
				v.getChildren().add(password);
				h.getChildren().add(exit);
				h.getChildren().add(back);
				h.getChildren().add(create);
				v.getChildren().add(h);
				root.getChildren().add(v);
				stage.setScene(scene);
				stage.show();
			}
		});
		
		create = new Button("Signup");
		
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MessageBox create = new MessageBox(Action.SIGN_UP);
				create.add(Data.USER_NAME, username.getText());
				username.clear();
				create.add(Data.PASSWORD, password.getText());
				password.clear();
				client.sendMessage(create);
				drawLogonScreen();
			}
		});
		
		logout = new Button("Log Out");
		
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				drawLogonScreen();
			}
		});
		
		chatName = new TextField("Enter chat name...");
		
		requestCreateChat = new Button("Create Chat");
		
		requestCreateChat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				drawCreateChatScreen();
			}
		});
		
		add = new Button("Add users");
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				drawAddUserScreen();
			}
		});
		
		invite = new Button("Invite");
		
		invite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MessageBox invite = new MessageBox(Action.INVITE);
				invite.add(Data.USER_NAME, username.getText());
				username.clear();
				invite.add(Data.CHAT_NAME, chatListView
										   .getSelectionModel()
										   .getSelectedItem());
				client.sendMessage(invite);
			}
		});
		
		createChat = new Button("Create");
		
		createChat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent eevent) {
				MessageBox create = new MessageBox(Action.START_NEW_CHAT);
				create.add(Data.CHAT_NAME, chatName.getText());
				create.add(Data.USER_NAME, loggedInName);
				observableChatList.add(chatName.getText());
				TextArea newMessageSpace = new TextArea();
				newMessageSpace.setEditable(false);
				messageSpaces.put(chatName.getText(), newMessageSpace);
				chatName.clear();
				client.sendMessage(create);
				createStage.close();
			}
		});
		
		cancel = new Button("Cancel");
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createStage.close();
				addStage.close();
			}
		});
		
		accept = new Button("Accept");
		
		accept.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MessageBox accept = new MessageBox(Action.ADD_USER);
				accept.add(Data.USER_NAME, username.getText());
				username.clear();
				accept.add(Data.CHAT_NAME, inviteName);
				client.sendMessage(accept);
				observableChatList.add(inviteName);
				inviteStage.close();
				messageSpaces.put(inviteName, new TextArea());
			}
		});
		
		decline = new Button("Decline");
		
		decline.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				inviteStage.close();
			}
		});
		
		v = new VBox();
		h = new HBox();
		hMain = new HBox();
		stage.setTitle("MessengerClient");
		drawLogonScreen();
	}
	
	public void drawLogonScreen() {
		Group root = new Group();
		v.getChildren().clear();
		h.getChildren().clear();
		v.getChildren().add(username);
		v.getChildren().add(password);
		v.getChildren().add(h);
		h.getChildren().add(exit);
		h.getChildren().add(login);
		h.getChildren().add(createAccount);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void drawCreateChatScreen() {
		Group root = new Group();
		VBox v = new VBox();
		v.getChildren().add(chatName);
		HBox h = new HBox();
		h.getChildren().add(cancel);
		h.getChildren().add(createChat);
		v.getChildren().add(h);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		createStage.setScene(scene);
		createStage.setTitle("Create Chat");
		createStage.show();
	}
	
	public void drawMainScreen(TextArea ta) {
		Group root = new Group();
		v.getChildren().clear();
		h.getChildren().clear();
		hMain.getChildren().clear();
		h.getChildren().add(input);
		h.getChildren().add(send);
		h.getChildren().add(logout);
		h.getChildren().add(requestCreateChat);
		h.getChildren().add(add);
		hMain.getChildren().add(chatListView);
		hMain.getChildren().add(ta);
		v.getChildren().add(hMain);
		v.getChildren().add(h);
		HBox h3 = new HBox();
		h3.getChildren().add(exit);
		h3.getChildren().add(loggedInAs);
		v.getChildren().add(h3);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void drawAddUserScreen() {
		Group root = new Group();
		VBox v = new VBox();
		v.getChildren().add(username);
		HBox h = new HBox();
		h.getChildren().add(cancel);
		h.getChildren().add(invite);
		v.getChildren().add(h);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		addStage.setScene(scene);
		addStage.show();
	}
	
	public void drawInviteScreen(MessageBox mb) {
		Group root = new Group();
		VBox v = new VBox();
		Text from = new Text(mb.get(Data.USER_NAME) + " invited you to chat.");
		v.getChildren().add(from);
		HBox h = new HBox();
		h.getChildren().add(accept);
		h.getChildren().add(decline);
		v.getChildren().add(h);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		inviteStage.setScene(scene);
		inviteStage.show();
	}
	
	public void displayMessage(MessageBox mb) {
//		if(mb.getAction() == Action.SERVER_MESSAGE) {
//			messageSpaces.get("global")
//			 .appendText(mb.get(Data.USER_NAME) + 
//					 ">>> " + mb.get(Data.MESSAGE) + "\n");
//		}
		if(mb.get(Data.CHAT_NAME) == null) {
			return;
		}
		messageSpaces.get(mb.get(Data.CHAT_NAME))
					 .appendText(mb.get(Data.USER_NAME) + 
							 ">>> " + mb.get(Data.MESSAGE) + "\n");
	}
	
	public void login() {
		loggedInAs = new Text("Logged in as " + loggedInName);
		drawMainScreen(messageSpaces.get("global"));
		messageSpaces.get("global").appendText("Login successful!" + "\n");
		input.requestFocus(); //moves focus to chat text field
	}
	
	public void refuseLogin() {
		login.setTextFill(Color.RED);
		messageSpace.appendText("Login refused." + "\n");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void setLoggedInName(String loggedInName) {
		this.loggedInName = loggedInName;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	
	public ObservableList<String> getObservableChatList() {
		return observableChatList;
	}
	
	public HashMap<String, TextArea> getMessageSpaces() {
		return messageSpaces;
	}
}
