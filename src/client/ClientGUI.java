package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.Action;
import server.Data;
import server.MessageBox;

public class ClientGUI extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Client client = new Client("localhost", 6000);
		
		TextField username = new TextField("Enter username...");
		TextField password = new TextField("Enter password...");
		Button login = new Button("Login");
		
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MessageBox login = new MessageBox(Action.LOGIN);
				login.add(Data.USER_NAME, username.getText());
				login.add(Data.PASSWORD, password.getText());
				client.getSender().sendMessage(login);
			}
		});
		
		Button exit = new Button("Exit");
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(1);
			}
		});
		
		Group root = new Group();
		VBox v = new VBox();
		HBox h = new HBox();
		v.getChildren().add(username);
		v.getChildren().add(password);
		v.getChildren().add(h);
		h.getChildren().add(exit);
		h.getChildren().add(login);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("MessengerClient");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
