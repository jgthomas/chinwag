package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OnlineIndicatorTreeCell extends TreeCell<String> {
	private HBox h = new HBox(4);
	private Label label = new Label();
	private Circle indicator;
	private Client client;
	
	public OnlineIndicatorTreeCell(Client client) {
		this.client = client;
		
		h.setAlignment(Pos.CENTER_LEFT);
		
		indicator = new Circle(4);
		indicator.setFill(Color.GREEN);
		
		h.getChildren().add(indicator);
		h.getChildren().add(this.label);
	}
	
	@Override 
	protected void updateItem(String label, boolean empty) {
		super.updateItem(label, empty);
		
		if(empty || label == null) {
			setGraphic(null);
		}
		else {
			this.label.setText(label);
			if(!this.getTreeItem().isLeaf() ||
					!client.getLoggedInUsers().contains(label)) {
				indicator.setVisible(false);
			}
			else
				indicator.setVisible(true);
			setGraphic(h);
		}
	}
}