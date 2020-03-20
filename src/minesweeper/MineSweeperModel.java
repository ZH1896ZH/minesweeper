package minesweeper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MineSweeperModel {

	protected Stage primaryStage;
	protected StackButton[][] grid;

	protected MineSweeperModel(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void reload() {// methode works LS

		MineSweeperView.grid = new StackButton[MineSweeperView.gridSize][MineSweeperView.gridSize];

		MineSweeperView.secondsPassed = 0;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				MineSweeperView.secondsPassed++;
			};
		};
		MineSweeperView.timer.cancel();
		MineSweeperView.timer = new Timer();
		MineSweeperView.timer.schedule(task, 1000, 1000);

		MineSweeperView.root.getChildren().remove(1);
		MineSweeperView.root.getChildren().add(createContent());
		MineSweeperView.primaryStage.sizeToScene();
	}

	protected static Parent createContent() { // Methode does work now RR
		// Reset in case of a new game
		MineSweeperView.numBombs = 0;
		MineSweeperView.foundBombs = 0;

		// das hat gefehlt --> darum NullPointerException!!
		MineSweeperView.grid = new StackButton[MineSweeperView.gridSize][MineSweeperView.gridSize];

		Pane secondroot = new Pane();
		secondroot.setPrefSize(MineSweeperView.gridSize * 40, MineSweeperView.gridSize * 40);

		// Create the StackButton and Bombs on percentage

		for (int i = 0; i < MineSweeperView.gridSize; i++) {
			for (int j = 0; j < MineSweeperView.gridSize; j++) {

				StackButton stackButton = new StackButton(j, i, Math.random() < (double) MineSweeperView.bombPercent);
				MineSweeperView.grid[j][i] = stackButton;
				secondroot.getChildren().add(stackButton);

			}
		}
		// add the value under the buttons and the color

		for (int i = 0; i < MineSweeperView.gridSize; i++) {
			for (int j = 0; j < MineSweeperView.gridSize; j++) {

				int bombNeighbours = 0;

				ArrayList<StackButton> neighbours = new ArrayList<StackButton>();

				int[] locs = new int[] { -1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1 };

				for (int l = 0; l < locs.length; l++) {
					int dx = locs[l];
					int dy = locs[++l];

					int newX = dx + j;
					int newY = dy + i;

					if (newX >= 0 && newX < MineSweeperView.gridSize && newY >= 0 && newY < MineSweeperView.gridSize) {
						neighbours.add(MineSweeperView.grid[newX][newY]);
						if (MineSweeperView.grid[newX][newY].hasBomb) {
							bombNeighbours++;
						}
					}
				}

				MineSweeperView.grid[j][i].numBombs = bombNeighbours;
				MineSweeperView.grid[j][i].neighbours = neighbours;

				Color[] colors = { null, Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE, Color.DARKRED, Color.CYAN,
						Color.BLACK, Color.DARKGRAY };

				MineSweeperView.grid[j][i].color = colors[MineSweeperView.grid[j][i].numBombs];
			}
		}
		return secondroot;

	}
}
