package minesweeper;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MineSweeperView {

	// MVC-branch
	protected Stage primaryStage;
	protected MineSweeperModel model;

	// game elements
	protected static boolean sound = true;
	protected static Timer timer;
	protected static int secondsPassed;
	protected static int bombPercent = 10;
	protected static int numBombs, foundBombs;

	// view-elements
	protected static VBox root = new VBox();
	protected MenuBar menuBar;
	protected Menu fileMenu, sizeMenu, difficultyMenu, soundMenu;
	protected MenuItem aboutItem, helpItem, quitItem, smallSizeItem, mediumSizeItem, largeSizeItem, easyItem,
			normalItem, hardItem;
	protected RadioMenuItem soundOnItem, soundOffItem;

	protected static StackButton[][] grid;
	protected static int gridSize = 10;

	protected MineSweeperView(Stage primaryStage, MineSweeperModel model) {
		this.primaryStage = primaryStage;
		this.model = model;

		// Menu-Instanziierung
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		sizeMenu = new Menu("Size");
		difficultyMenu = new Menu("Difficulty");
		soundMenu = new Menu("Sound");
		aboutItem = new MenuItem("About");
		helpItem = new MenuItem("Help");
		quitItem = new MenuItem("Quit");
		smallSizeItem = new MenuItem("Small (10x10)");
		mediumSizeItem = new MenuItem("Medium (15x15)");
		largeSizeItem = new MenuItem("Large (20x20)");
		easyItem = new MenuItem("Easy");
		normalItem = new MenuItem("Normal");
		hardItem = new MenuItem("Hard");
		soundOnItem = new RadioMenuItem("On");
		soundOffItem = new RadioMenuItem("Off");

		fileMenu.getItems().addAll(aboutItem, helpItem, quitItem);
		sizeMenu.getItems().addAll(smallSizeItem, mediumSizeItem, largeSizeItem);
		difficultyMenu.getItems().addAll(easyItem, normalItem, hardItem);
		soundMenu.getItems().addAll(soundOnItem, soundOffItem);

		ToggleGroup soundToggle = new ToggleGroup();
		soundToggle.getToggles().addAll(soundOnItem, soundOffItem);
		soundToggle.selectToggle(soundOnItem);

		menuBar.getMenus().addAll(fileMenu, sizeMenu, difficultyMenu, soundMenu);

		// MenuBar und Buttons werden der VBox(root) hinzugefügt
		root.getChildren().addAll(menuBar, model.createContent()); // Buttons-Instanziierung in einer Pane (RR) geändert zu meienr methodeLS

		// Szene instanziieren und an Stage weitergeben
		Scene scene = new Scene(root, 400, 425);
		scene.getStylesheets().add(getClass().getResource("/resources/MineSweeperStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");
	}

	protected void start() {
		primaryStage.show();
	}

	protected static void reload() {

		grid = new StackButton[gridSize][gridSize];

		secondsPassed = 0;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				secondsPassed++;
			};
		};
		timer.cancel();
		timer = new Timer();
		timer.schedule(task, 1000, 1000);

		root.getChildren().remove(1);
		root.getChildren().add(createContent());
	}

	/**
	 * Create all the tiles and assign bombs accordingly
	 * 
	 * @return root - The playing field
	 */
	private static Pane createContent() { //why here

		// Reset to zero in case of new game.
		numBombs = 0;
		foundBombs = 0;
		return createPane();
	}

	private static Pane createPane() {// why here

		grid = new StackButton[gridSize][gridSize];
		Pane buttonContainer = new Pane();
		buttonContainer.setPrefSize(gridSize * 40, gridSize * 40);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				StackButton stackButton = new StackButton(x, y, Math.random() < (double) bombPercent / 100);
				grid[x][y] = stackButton;
				buttonContainer.getChildren().add(stackButton);
			}
		}

		return buttonContainer;
	}

	protected Stage getStage() {
		return primaryStage;
	}

}
