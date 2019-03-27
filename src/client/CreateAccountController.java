package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class CreateAccountController {
	Client client;
	private Stage stage;
	private LoginController controller;
	
	
	@FXML private Button back;
	@FXML private Button signup;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Text notLetters;
	@FXML private Text duplicateUsername;
	
	public CreateAccountController(Client client, Stage stage) {
		this.client = client;
		this.stage = stage;
	}
	
	public void initialize() {
		notLetters.setVisible(false);
		duplicateUsername.setVisible(false);
	}
	
	public void back(ActionEvent e) throws IOException {
		drawLogonScreen();
	}
	
	public void sendSignupRequest(ActionEvent e) {
		if (MainController.checkUserInput(usernameField.getText())) {
			MessageBox create = new MessageBox(Action.SIGN_UP);
			create.add(Data.USER_NAME, usernameField.getText());
			usernameField.clear();
			create.add(Data.PASSWORD, passwordField.getText());
			passwordField.clear();
			client.sendMessage(create);
			drawLogonScreen();
		}
		else {
			usernameField.clear();
			notLetters.setVisible(true);
		}
	}
	
	private void drawLogonScreen(){
		LoginController controller = new LoginController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
