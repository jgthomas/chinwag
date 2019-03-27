package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	private Scene scene;
	
	@Override 
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		LoginController controller = new LoginController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		
		scene = new Scene(root);
		stage.setTitle("ChinWag");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Used to make sure users can't create usernames or chat names with non-letter characters.
	 * 
	 * @param user's input
	 * @return true iff user's input consists only of letters.
	 */
	public static boolean checkUserInput(String input) {
		char[] characters = input.toCharArray();
		for (char character: characters) {
			if (!Character.isLetter(character)) {
				return false;
			}
		}
		return true;
	}
}
