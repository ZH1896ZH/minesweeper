package minesweeper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class StackButton extends StackPane {

	protected Button btn = new Button();
	protected int x, y = 0;
	protected boolean hasBomb;
	protected static final int sideLength = 40;

	protected int numBombs = 0;
	protected static int numflag = 0;
	protected static int maxflag;

	protected Color color = null;
	protected boolean flagged = false;
	protected boolean active = true;
	protected ArrayList<StackButton> neighbours = new ArrayList<StackButton>();

	protected Image flag = new Image("/resources/flag.png");

	protected StackButton(int x, int y, boolean hasBomb) {
		this.x = x;
		this.y = y;
		this.hasBomb = hasBomb;

		if (hasBomb) {
			View.numBombs++;
			maxflag = View.numBombs;
		}

		btn.setMinHeight(sideLength);
		btn.setMinWidth(sideLength);

		btn.setOnMouseClicked(e -> {
			onClick(e);
		});

		btn.setOnKeyPressed(e -> {
			onPressed(e);
		});
		btn.setOnMouseEntered(e -> {
			btn.getStyleClass().add("hasMouse");

		});
		btn.setOnMouseExited(e -> {
			btn.getStyleClass().remove("hasMouse");
		});

		this.getChildren().add(btn);

		this.setTranslateX(x * sideLength);
		this.setTranslateY(y * sideLength);
	}

	// Keyboard Control
	private void onPressed(KeyEvent e) {

		// Enter Key
		if (e.getCode() == KeyCode.ENTER) {
			clickSound();
			openStackButton();

			// Shift Key
		} else if (e.getCode() == KeyCode.SHIFT) {
			clickSound();
			flagStackButton();
		}

	}

	// Mouse Control
	protected void onClick(MouseEvent e) {

		clickSound();

		// Left Click
		if (e.getButton() == MouseButton.PRIMARY) {
			openStackButton();
		}
		// Right Click
		else {
			flagStackButton();

		}
	}

	// Opens all the StackButtons around the actual Click until a Bomb isNeighbour
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
				if (stackButton.neighbours.get(i).flagged) {
					numflag--;
				}
			}

		}
		return;
	}

	// When all Flags are used, but there are still unflagged Bombs --> ALERT
	private void showFlagAlert() {
		Alert flagAlert = new Alert(AlertType.INFORMATION);
		flagAlert.setTitle("Flaggen Alarm");
		flagAlert.setGraphic(new ImageView(flag));
		flagAlert.setHeaderText("Maximale Anzahl Flaggen erreicht");
		flagAlert.setContentText(
				"Du hast zu viele Flaggen platziert, du musst zuerst eine entfernen bevor du wieder eine platzieren kannst");
		flagAlert.showAndWait();
	}

	private void openStackButton() {
		if (!flagged) {

			btn.setBackground(null);
			btn.setDisable(true);
			active = false;

			if (hasBomb) {
				gameOver();
			} else {
				// Blank
				if (numBombs == 0) {
					blankClick(this);
				} else {
					btn.setText(Integer.toString(numBombs));
					btn.setTextFill(color);
				}
			}
		}
	}

	private void flagStackButton() {
		if (numflag < maxflag) {
			if (!flagged) {
				flagged = true;

				if (numflag < maxflag) {
					numflag++;
				} else {
					showFlagAlert();
				}

				btn.setGraphic(new ImageView(flag));
				if (this.hasBomb) {
					View.foundBombs++;
					if (View.foundBombs == View.numBombs) {
						win();
					}
				}
			} else {
				if (hasBomb) {
					View.foundBombs--;
				}
				btn.setGraphic(null);
				flagged = false;
				numflag--;
			}
		} else {
			if (flagged) {
				if (hasBomb) {
					View.foundBombs--;
				}
				btn.setGraphic(null);
				flagged = false;
				numflag--;
			} else {
				showFlagAlert();
			}
		}
	}

	private void clickSound() {
		if (View.sound) {
			AudioClip click = new AudioClip(getClass().getResource("/resources/click.wav").toString());
			click.play();
		}
	}

	// Player clicked on a Bomb
	public void gameOver() {
		if (View.sound) {
			AudioClip explosion = new AudioClip(getClass().getResource("/resources/explosion.wav").toString());
			explosion.play();
		}
		for (int y = 0; y < View.gridSize; y++) {
			for (int x = 0; x < View.gridSize; x++) {
				if (View.grid[x][y].hasBomb) {
					View.grid[x][y].btn.setGraphic(new ImageView(View.mine)); // Display all Bombs
					View.grid[x][y].btn.setDisable(true);
				}
			}
		}
		View.timer.cancel();
		Alert gameOver = new Alert(AlertType.INFORMATION);
		gameOver.setTitle("Niederlage!");
		gameOver.setGraphic(new ImageView(View.mine));
		gameOver.setHeaderText("Bomben sind explodiert!");
		gameOver.setContentText(
				"Du hast auf eine Bombe geklickt und alle Bomben sind explodiert! Viel Glück beim nächsten Mal.");
		gameOver.showAndWait();

		Model.reload();

	}

	// Player flagged all Bombs
	public void win() {

		DecimalFormat secFormater = new DecimalFormat("00");
		DecimalFormat minFormater = new DecimalFormat("#0");
		View.timer.cancel();
		if (View.sound) {
			AudioClip winSound = new AudioClip(getClass().getResource("/resources/win.wav").toString());
			winSound.play();
		}
		Alert win = new Alert(AlertType.CONFIRMATION);
		win.getButtonTypes().remove(ButtonType.CANCEL); // Button "Abbrechen" is not needed
		win.setTitle("Sieg!");
		win.setGraphic(new ImageView(flag));
		win.setHeaderText("Gratulation!");
		win.setContentText("Du hast alle Bomben in " + minFormater.format(View.minutesPassedObj.get()) + ":"
				+ secFormater.format(View.secondsPassedObj.get()) + " Minuten gefunden!");
		win.showAndWait();

		Model.reload();
	}

}
