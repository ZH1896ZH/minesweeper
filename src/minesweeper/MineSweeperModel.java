package minesweeper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MineSweeperModel {

	protected Stage primaryStage;
	protected StackButton[][] grid;
	protected StackButton stackButton;

	protected MineSweeperModel(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	protected static void reload() {

		MineSweeperView.grid = new StackButton[MineSweeperView.gridSize][MineSweeperView.gridSize];

		MineSweeperView.minutesPassedObj.set(0);
		MineSweeperView.secondsPassedObj.set(0);

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				MineSweeperView.secondsPassedObj.set(MineSweeperView.secondsPassedObj.get() + 1);
				if (MineSweeperView.secondsPassedObj.get() == 60) {
					MineSweeperView.secondsPassedObj.set(0);
					MineSweeperView.minutesPassedObj.set(MineSweeperView.minutesPassedObj.get() + 1);
				}
			};
		};
		MineSweeperView.timer.cancel();
		MineSweeperView.timer = new Timer();
		MineSweeperView.timer.schedule(task, 1000, 1000);
		MineSweeperView.root.getChildren().remove(2);// remove 1 wenn MenuBar nicht da wäre
		MineSweeperView.root.getChildren().add(createContent());
		MineSweeperView.stage.sizeToScene();
	}

	protected static Pane createContent() {
		// Reset these in case of a new game
		MineSweeperView.numBombs = 0;
		MineSweeperView.foundBombs = 0;

		// das hat gefehlt --> darum NullPointerException!!
		MineSweeperView.grid = new StackButton[MineSweeperView.gridSize][MineSweeperView.gridSize];

		Pane pane = new Pane();
		pane.setPrefSize(MineSweeperView.gridSize * StackButton.prefSize, MineSweeperView.gridSize * StackButton.prefSize);

		// fill the grid with StackButtons
		for (int x = 0; x < MineSweeperView.gridSize; x++) {
			for (int y = 0; y < MineSweeperView.gridSize; y++) {
		// boolean hasBomb -> random number (0.0 - 1) lower than bombPercent
				StackButton stackButton = new StackButton(y, x, Math.random() < (double) MineSweeperView.bombPercent / 100);
				MineSweeperView.grid[y][x] = stackButton;
				pane.getChildren().add(stackButton);
			}
		}
//		
	

		// count the number of bombs and set the values and colors
		// add the value under the buttons and the color
		for (int x = 0; x < MineSweeperView.gridSize; x++) {
			for (int y = 0; y < MineSweeperView.gridSize; y++) {

				int bombNeighbors = 0;
				// get the neighbors of a given stackButton and put them in a list
				ArrayList<StackButton> neighbors = new ArrayList<StackButton>();
				/**
				 * get the difference of the x, y values of the neighbors to the x, y values of
				 * the given stackButton
				 *
				 * (-1,-1) (0,-1) (1,-1) 
				 * (-1,0) (x) (1,0) 
				 * (-1,1) (0,1) (1,1)
				 */
				int[] points = new int[] { -1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1 };
				for (int i = 0; i < points.length; i++) {
					int differenceX = points[i];
					int differenceY = points[++i];
					int newX = x + differenceX;
					int newY = y + differenceY;

					// check if x, y values are inside grid --> isValidCoordinate
					if (newX >= 0 && newX < MineSweeperView.gridSize) {
						if (newY >= 0 && newY < MineSweeperView.gridSize) {
							neighbors.add(MineSweeperView.grid[newY][newX]);
							if (MineSweeperView.grid[newY][newX].hasBomb) {
								bombNeighbors++;
							}
						}
					}
				}

				MineSweeperView.grid[y][x].numBombs = bombNeighbors;
				MineSweeperView.grid[y][x].neighbours = neighbors;

				Color[] colors = { null, Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE, Color.DARKRED, Color.CYAN,
						Color.BLACK, Color.DARKGRAY };

				MineSweeperView.grid[y][x].color = colors[MineSweeperView.grid[y][x].numBombs];
			}
		}
		return pane;

	}

}
