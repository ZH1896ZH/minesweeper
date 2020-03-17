package minesweeper;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class StackButton extends StackPane {

	protected Button btn = new Button();
	protected int x = 0, y = 0;
	protected boolean hasBomb;
	protected int numBombs = 0;
	protected Color color = null;
	protected boolean flagged = false;
	protected ArrayList<StackButton> neighbours = new ArrayList<StackButton>();
	protected boolean active = true;
	

	protected StackButton(int x, int y, boolean hasBomb) {
		this.x = x;
		this.y = y;
		this.hasBomb = hasBomb;
		
		btn.setMinHeight(40);
		btn.setMinWidth(40);
		
		this.getChildren().add(btn);
		this.setTranslateX(x * 40);
		this.setTranslateY(y * 40);
	}
}
