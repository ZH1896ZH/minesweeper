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
	protected int numBombs = 0;
	protected Color color = null;
	protected boolean flagged = false;
	protected ArrayList<StackButton> neighbours = new ArrayList<StackButton>();
	protected boolean active = true;
	protected static final int prefSize = 40;
	protected static int numflag = 0;
	protected static int maxflag;
	protected Image flag = new Image("/resources/flag.png");

	protected StackButton(int x, int y, boolean hasBomb) {
		this.x = x;
		this.y = y;
		this.hasBomb = hasBomb;

		if (hasBomb) {
			View.numBombs++;
			maxflag = View.numBombs;
		}

		btn.setMinHeight(prefSize);
		btn.setMinWidth(prefSize);

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

		this.getChildren().addAll(btn);

		this.setTranslateX(x * prefSize);
		this.setTranslateY(y * prefSize);
	}

	//Keyboard Control
	private void onPressed(KeyEvent e) {
		//Enter Key
		if (e.getCode() == KeyCode.ENTER) {
			openStackButton();

		//Shift Key
		} else if (e.getCode() == KeyCode.SHIFT) {
			flagStackButton();
		}

	}




	protected void onClick(MouseEvent e) {

		if (View.sound) {
			AudioClip click = new AudioClip(getClass().getResource("/resources/click.wav").toString());
			click.play();
		}

		//Left Click
		if (e.getButton() == MouseButton.PRIMARY) {
			openStackButton();
		}
		//Right Click
		else {
			flagStackButton();

		}
	}

	//open all StackButton until there is a Bomb near
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


	
	
	//Information gets displayed when all Flags are used, but there are still unflagged Bombs
	private void showFlagAlert() {
		Alert test = new Alert(AlertType.INFORMATION);
		test.setTitle("Flaggen Alarm");
		test.setGraphic(new ImageView(flag));
		test.setHeaderText("Maximale Anzahl Flaggen erreicht");
		test.setContentText(
				"Du hast zu viele Flaggen platziert, du musst zuerst eine entfernen bevor du wieder eine platzieren kannst");
		test.showAndWait();
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
					Alert test = new Alert(AlertType.INFORMATION);
					test.setTitle("Flaggen Anzahl");
					test.setGraphic(new ImageView(flag));
					test.setHeaderText("Zu viele Flaggen");
					test.setContentText(
							"Du hast zu viele Flaggen plaziert, du musst zuerst eine entfernen bevor du wieder eine plazieren kannst");
					test.showAndWait();
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
	//player clicked on a bomb
		public void gameOver() {
			if (View.sound) {
				AudioClip explosion = new AudioClip(getClass().getResource("/resources/explosion.wav").toString());
				explosion.play();
			}
			for (int y = 0; y < View.gridSize; y++) {
				for (int x = 0; x < View.gridSize; x++) {
					if (View.grid[x][y].hasBomb) {
						View.grid[x][y].btn.setGraphic(new ImageView(View.mine));
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
					"Du hast auf eine Bombe geklickt und alle Bomben sind explodiert! Viel Gl�ck beim n�chsten Mal.");
			gameOver.showAndWait();

			Model.reload();

		}

		//if the player found all bombs
		public void win() {

			DecimalFormat fmt = new DecimalFormat("00");
			DecimalFormat fmtt = new DecimalFormat("#0");
			View.timer.cancel();
			if (View.sound) {
				AudioClip winSound = new AudioClip(getClass().getResource("/resources/win.wav").toString());
				winSound.play();
			}
			Alert win = new Alert(AlertType.CONFIRMATION);
			win.getButtonTypes().remove(ButtonType.CANCEL); //Button "Abbrechen" is not needed
			win.setTitle("Sieg!");
			win.setGraphic(new ImageView(flag));
			win.setHeaderText("Gratulation!");
			win.setContentText("Du hast alle Bomben in " + fmtt.format(View.minutesPassedObj.get()) + ":"
					+ fmt.format(View.secondsPassedObj.get()) + " Minuten gefunden.");
			win.showAndWait();
			Model.reload();
		}

}
