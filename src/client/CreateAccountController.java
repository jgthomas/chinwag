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
	private Client client;
	private Stage stage;
	private LoginController controller;
	
	
	@FXML private Button back;
	@FXML private Button signup;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Text notLetters;
	@FXML private Text duplicateUsername;
	@FXML private Text usernameTooLong;
	
	public CreateAccountController(Client client, Stage stage, LoginController controller) {
		this.client = client;
		this.stage = stage;
		this.controller = controller;
	}
	
	public void initialize() {
		notLetters.setVisible(false);
		duplicateUsername.setVisible(false);
		usernameTooLong.setVisible(false);
	}
	
	public void back(ActionEvent e) throws IOException {
		stage.close();
		drawLogonScreen(false);
	}
	
	public void sendSignupRequest(ActionEvent e) {
		if (usernameField.getText().length() > 10) {
			notLetters.setVisible(false);
			duplicateUsername.setVisible(false);
			usernameTooLong.setVisible(true);
			usernameField.clear();
			return;
		}
		else if (MainController.checkUserInput(usernameField.getText())) {
			MessageBox create = new MessageBox(Action.SIGN_UP);
			create.add(Data.USER_NAME, usernameField.getText());
			usernameField.clear();
			create.add(Data.PASSWORD, passwordField.getText());
			passwordField.clear();
			client.sendMessage(create);
		}
		else {
			usernameField.clear();
			usernameTooLong.setVisible(false);
			duplicateUsername.setVisible(false);
			notLetters.setVisible(true);
			usernameField.requestFocus();
		}
	}
	
	public void drawLogonScreen(boolean justSignedUpSuccessfully){
		LoginController controller = new LoginController(stage, client);
		client.setController(controller);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			if (justSignedUpSuccessfully) {
				controller.setSuccessfulSignupVisible();
			}
			stage.close();
			stage.setTitle("ChinWag");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void displayDuplicateUser() {
		usernameTooLong.setVisible(false);
		notLetters.setVisible(false);
		duplicateUsername.setVisible(true);
	}
	
	public void displaySuccessfulSignUp() {
		controller.setSuccessfulSignupVisible();
	}
}
