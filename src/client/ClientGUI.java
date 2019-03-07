package client;

import javafx.application.Application;
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
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class ClientGUI extends Application {
	private Button login;
	private Button exit;
	private Button send;
	private TextField username;
	private TextField password;
	private TextField input;
	private TextArea messageSpace;
	private VBox v;
	
	@Override
	public void start(Stage stage) throws Exception {
		username = new TextField("Enter username...");
		password = new TextField("Enter password...");
		input = new TextField("Enter username...");
		
		messageSpace = new TextArea();
		messageSpace.setEditable(false);
		
		login = new Button("Login");
		
		Client client = new Client("localhost", 6000, this);
		
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MessageBox login = new MessageBox(Action.LOGIN);
				login.add(Data.USER_NAME, username.getText());
				username.clear();
				login.add(Data.PASSWORD, password.getText());
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
			public void handle(ActionEvent event) {
				MessageBox message = new MessageBox(Action.CHAT);
				message.add(Data.CHAT_NAME, "global");
				message.add(Data.MESSAGE, username.getText());
				username.clear();
				client.getSender().sendMessage(message);
			}
		});
		
		Group root = new Group();
		v = new VBox();
		HBox h = new HBox();
		HBox h2 = new HBox();
		v.getChildren().add(messageSpace);
		v.getChildren().add(username);
		v.getChildren().add(password);
		v.getChildren().add(h);
		h.getChildren().add(exit);
		h.getChildren().add(login);
		h.getChildren().add(send);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("MessengerClient");
		stage.show();
	}
	
	public void displayMessage(String message) {
		messageSpace.appendText("\n" + message);
	}
	
	public void login() {
		login.setTextFill(Color.GREEN);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
