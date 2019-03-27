package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	private Scene scene;
	private Client client;
	private LoginController controller;
	
	@Override 
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		client = new Client("localhost", 6000);
		controller = new LoginController(stage, client);
		client.setController(controller);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		
		scene = new Scene(root);
		stage.setTitle("ChinWag");
		stage.setScene(scene);
		stage.setOnCloseRequest(ActionEvent -> System.exit(1));
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
	
}
