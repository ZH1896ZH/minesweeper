package minesweeper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class StackButton extends StackPane {
	protected MineSweeperModel model;

	protected Button btn = new Button();
	protected int x, y = 0;
	protected boolean hasBomb;
	protected int numBombs = 0;
	protected Color color = null;
	protected boolean flagged = false;
	protected ArrayList<StackButton> neighbours = new ArrayList<StackButton>();
	protected boolean active = true;

	protected static Image flag = new Image("/resources/flag.png");

	protected StackButton(int x, int y, boolean hasBomb) {
		this.x = x;
		this.y = y;
		this.hasBomb = hasBomb;
		
		if(hasBomb) {
			MineSweeperView.numBombs++;
		}

		btn.setMinHeight(40);
		btn.setMinWidth(40);
		
		btn.setOnMouseClicked(e -> {
			onClick(e);
		});

		this.getChildren().addAll(btn);
		
		this.setTranslateX(x * 40);
		this.setTranslateY(y * 40);
	}

	
	protected void onClick(MouseEvent e) {

		if (MineSweeperView.sound) { // when sound on
			AudioClip click = new AudioClip(getClass().getResource("/resources/click.wav").toString());
			click.play();
		}

		// Left Click
		if (e.getButton() == MouseButton.PRIMARY) {
			if (!flagged) {

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

	/**
	 * Runs when a player left clicks a bomb. Reveals all bomb tiles and displays
	 * message. Calls to reload the game.
	 */
	public void gameOver() {
		if (MineSweeperView.sound) {
			AudioClip explosion = new AudioClip(getClass().getResource("/resources/explosion.wav").toString());
			explosion.play();
		}
		for (int y = 0; y < MineSweeperView.gridSize; y++) {
			for (int x = 0; x < MineSweeperView.gridSize; x++) {
				if (MineSweeperView.grid[x][y].hasBomb) {
					MineSweeperView.grid[x][y].btn.setGraphic(new ImageView(MineSweeperView.mine));
					MineSweeperView.grid[x][y].btn.setDisable(true);
				}
			}
		}

		Alert gameOver = new Alert(AlertType.INFORMATION);
		gameOver.setTitle("Game Over!");
		gameOver.setGraphic(new ImageView(MineSweeperView.mine));
		gameOver.setHeaderText("Bomb Exploded!");
		gameOver.setContentText(
				"Oh no! You clicked on a bomb and caused all the bombs to explode! Better luck next time.");
		gameOver.showAndWait();

		MineSweeperModel.reload();

	}

	/**
	 * Player win. Displays message. Calls to reload the game.
	 */
	public void win() {// auch gewinnen wenn nicht alle bomben markiert sind aber alle anderen felder offen
		
		DecimalFormat fmt = new DecimalFormat("#0");

		if (MineSweeperView.sound) {
			AudioClip winSound = new AudioClip(getClass().getResource("/resources/win.wav").toString());
			winSound.play();
		}
		Alert win = new Alert(AlertType.CONFIRMATION);
		win.setTitle("Win!");
		win.setGraphic(new ImageView(flag));
		win.setHeaderText("Congratulations!");
		win.setContentText("You found all the bombs in " + fmt.format(MineSweeperView.minutesPassed) + ":" + fmt.format(MineSweeperView.secondsPassed) + " minutes.");
		win.showAndWait();
		MineSweeperModel.reload();
	}

}
