package minesweeper;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
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

	protected static Image flag = new Image("resources/flag.png");

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

	protected void onClick(MouseEvent e) {

	if (MineSweeperView.sound) { //when sound on
		AudioClip click = new AudioClip(getClass().getResource("/resources/click.wav").toString());
			click.play();
		}

	// Left Click
		if (e.getButton() == MouseButton.PRIMARY) {
			if(!flagged) {

			btn.setBackground(null);
			btn.setDisable(true);
			active = false;

			if (hasBomb) {
				gameOver();
			} else {
				// Blank
				if (this.numBombs == 0) {
					blankClick(this);
				} else {
					btn.setText(Integer.toString(numBombs));
					btn.setTextFill(color);
				}
			}
			}
		}
		// Right Click
		else {
			if (!flagged) {
				flagged = true;
				btn.setGraphic(new ImageView(flag));
				if (this.hasBomb) {
					MineSweeperView.foundBombs++;
					if (MineSweeperView.foundBombs == MineSweeperView.numBombs) {
						win();
					}
				}
			} else {
				if (hasBomb) {
					MineSweeperView.foundBombs--;
				}
				btn.setGraphic(null);
				flagged = false;
			}
		}
	}

	private void blankClick(StackButton stackButton) {

		for (int i = 0; i < stackButton.neighbours.size(); i++) {
			if (stackButton.neighbours.get(i).active) {
				stackButton.neighbours.get(i).btn.setDisable(true);
				stackButton.neighbours.get(i).btn.setGraphic(null);
				stackButton.neighbours.get(i).btn.setText(Integer.toString(stackButton.neighbours.get(i).numBombs));
				stackButton.neighbours.get(i).btn.setTextFill(stackButton.neighbours.get(i).color);
				stackButton.neighbours.get(i).active = false;
				if (stackButton.neighbours.get(i).numBombs == 0) {
					blankClick(stackButton.neighbours.get(i));
				}

			}
		}
		return;
	}

	private void win() {
		// TODO Auto-generated method stub

	}

	private void gameOver() {
		// TODO Auto-generated method stub

	}

	public void onClicked(MouseEvent e) {
		System.out.println("test");
	}

}
