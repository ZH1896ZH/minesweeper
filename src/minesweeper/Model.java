package minesweeper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Model {

	protected Stage primaryStage;
	protected StackButton[][] grid;
	protected StackButton stackButton;

	protected Model(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	protected static void reload() {

		View.grid = new StackButton[View.gridSize][View.gridSize];

		View.minutesPassedObj.set(0);
		View.secondsPassedObj.set(0);

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				View.secondsPassedObj.set(View.secondsPassedObj.get() + 1);
				if (View.secondsPassedObj.get() == 60) {
					View.secondsPassedObj.set(0);
					View.minutesPassedObj.set(View.minutesPassedObj.get() + 1);
				}
			};
		};
		View.timer.cancel();
		View.timer = new Timer();
		View.timer.schedule(task, 1000, 1000);
		View.root.getChildren().remove(2);// remove 1 wenn MenuBar nicht da wäre
		View.root.getChildren().add(createContent());
		View.stage.sizeToScene();
	}

	protected static Pane createContent() {
		// Reset these in case of a new game
		View.numBombs = 0;
		View.foundBombs = 0;

		// das hat gefehlt --> darum NullPointerException!!
		View.grid = new StackButton[View.gridSize][View.gridSize];

		Pane pane = new Pane();
		pane.setPrefSize(View.gridSize * StackButton.prefSize, View.gridSize * StackButton.prefSize);

		// fill the grid with StackButtons
		for (int x = 0; x < View.gridSize; x++) {
			for (int y = 0; y < View.gridSize; y++) {
		// boolean hasBomb -> random number (0.0 - 1) lower than bombPercent
				StackButton stackButton = new StackButton(y, x, Math.random() < (double) View.bombPercent / 100);
				View.grid[y][x] = stackButton;
				pane.getChildren().add(stackButton);
			}
		}
//		
	

		// count the number of bombs and set the values and colors
		// add the value under the buttons and the color
		for (int x = 0; x < View.gridSize; x++) {
			for (int y = 0; y < View.gridSize; y++) {

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
					if (newX >= 0 && newX < View.gridSize) {
						if (newY >= 0 && newY < View.gridSize) {
							neighbors.add(View.grid[newY][newX]);
							if (View.grid[newY][newX].hasBomb) {
								bombNeighbors++;
							}
						}
					}
				}

				View.grid[y][x].numBombs = bombNeighbors;
				View.grid[y][x].neighbours = neighbors;

				Color[] colors = { null, Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE, Color.DARKRED, Color.CYAN,
						Color.BLACK, Color.DARKGRAY };

				View.grid[y][x].color = colors[View.grid[y][x].numBombs];
			}
		}
		return pane;

	}

}
