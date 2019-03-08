package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextField username;
	private TextField password;
	private TextField input;
	private TextArea messageSpace;
	private VBox v;
	private HBox h;
	private Stage stage;
	private String loggedInName;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		username = new TextField("Enter username...");
		password = new TextField("Enter password...");
		input = new TextField("Enter message...");
		
		messageSpace = new TextArea();
		messageSpace.setEditable(false);
		
		login = new Button("Login");
		
		Client client = new Client("localhost", 6000, this);
		
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MessageBox login = new MessageBox(Action.LOGIN);
				login.add(Data.USER_NAME, username.getText());
				username.clear();
				login.add(Data.PASSWORD, password.getText() + "");
				password.clear();
				client.getSender().sendMessage(login);
			}
		});
		
		exit = new Button("Exit");
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(1);
			}
		});
		
		send = new Button("Send");
		
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				messageSpace.appendText("\n" + "You>>> " + input.getText());
				MessageBox message = new MessageBox(Action.CHAT);
				message.add(Data.CHAT_NAME, "global");
				message.add(Data.USER_NAME, loggedInName);
				message.add(Data.MESSAGE, input.getText());
				input.clear();
				client.getSender().sendMessage(message);
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
				client.getSender().sendMessage(create);
			}
		});
		
		logout = new Button("Log Out");
		
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				drawLogonScreen();
			}
		});
		
		v = new VBox();
		h = new HBox();
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
	
	public void drawMainScreen() {
		Group root = new Group();
		v.getChildren().clear();
		v.getChildren().add(messageSpace);
		h.getChildren().clear();
		h.getChildren().add(input);
		h.getChildren().add(send);
		h.getChildren().add(logout);
		v.getChildren().add(h);
		v.getChildren().add(exit);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void displayMessage(String user, String message) {
		messageSpace.appendText("\n" + user + ">>> " + message);
	}
	
	public void login() {
		drawMainScreen();
		messageSpace.appendText("Login successful!" + "\n");
	}
	
	public void refuseLogin() {
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
}
