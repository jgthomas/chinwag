package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OnlineIndicatorListCell extends ListCell<String> {
	private HBox h = new HBox(8);
	private Label user = new Label();
	private Circle indicator;
	
	public OnlineIndicatorListCell() {
		h.setAlignment(Pos.CENTER_LEFT);
		
		indicator = new Circle(4);
		indicator.setFill(Color.GREEN);
		
		h.getChildren().add(indicator);
		h.getChildren().add(user);
		
		setGraphic(h);
	}
	
	@Override 
	protected void updateItem(String user, boolean empty) {
		super.updateItem(user, empty);
		
		if(empty || user == null) {
			setGraphic(null);
		}
		else {
			this.user.setText(user);
			setGraphic(h);
		}
	}
}
