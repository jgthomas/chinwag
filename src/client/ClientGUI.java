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
import server.Action;
import server.Data;
import server.MessageBox;

public class ClientGUI extends Application {
	private Button login;
	private Button exit;
	private TextField username;
	private TextField password;
	private TextArea messageSpace;
	private VBox v;
	
	@Override
	public void start(Stage stage) throws Exception {
		username = new TextField("Enter username...");
		password = new TextField("Enter password...");
		
		messageSpace = new TextArea();
		messageSpace.setEditable(false);
		
		login = new Button("Login");
		
		Client client = new Client("localhost", 6000, this);
		
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MessageBox login = new MessageBox(Action.LOGIN);
				login.add(Data.USER_NAME, username.getText());
				login.add(Data.PASSWORD, password.getText());
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
		
		Group root = new Group();
		v = new VBox();
		HBox h = new HBox();
		v.getChildren().add(username);
		v.getChildren().add(password);
		v.getChildren().add(h);
		v.getChildren().add(messageSpace);
		h.getChildren().add(exit);
		h.getChildren().add(login);
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
