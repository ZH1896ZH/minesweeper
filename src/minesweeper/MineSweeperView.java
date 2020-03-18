package minesweeper;

import java.util.Timer;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MineSweeperView {

	protected Stage primaryStage;
	protected MineSweeperModel model;

	public static int gridSize = 10;
	public static int bombPercent = 10;

	public static StackButton[][] grid;

	public static boolean sound = true;
	public static Timer timer;
	public static int secondPassed;

	private VBox root = new VBox();
	protected MenuBar menuBar;
	protected Menu fileMenu, sizeMenu, difficultyMenu, soundMenu;
	protected MenuItem aboutItem, helpItem, quitItem, smallSizeItem, mediumSizeItem, largeSizeItem, easyItem,
			normalItem, hardItem;
	protected RadioMenuItem soundOnItem, soundOffItem;

	public MineSweeperView(Stage primaryStage) {// remove after testing the view (start)
		this.primaryStage = primaryStage;

		// Menu-Instanziierung
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		sizeMenu = new Menu("Size");
		difficultyMenu = new Menu("Difficulty");
		soundMenu = new Menu("Sound");
		aboutItem = new MenuItem("About");
		helpItem = new MenuItem("help");
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
		
		
		// Buttons-Instanziierung, noch in Arbeit ;) (RR)
		grid = new StackButton[gridSize][gridSize];
		Pane buttonContainer = new Pane();
		buttonContainer.setPrefSize(gridSize * 40, gridSize * 40);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				StackButton stackButton = new StackButton(x, y, true); //hasBomb noch �ndern
				grid[x][y] = stackButton;
				buttonContainer.getChildren().add(stackButton);
			}

		}
		//MenuBar und Buttons werden der VBox hinzugef�gt
		root.getChildren().addAll(menuBar, buttonContainer);

		

		Scene scene = new Scene(root, 400, 425);
		scene.getStylesheets().add(getClass().getResource("/resources/MineSweeperStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");

	}// (end)

	protected MineSweeperView(Stage primaryStage, MineSweeperModel model) {
		this.primaryStage = primaryStage;
		this.model = model;

		// Szene instanziieren und an Stage weitergeben
		Scene scene = new Scene(root, 400, 425);
		scene.getStylesheets().add(getClass().getResource("/resources/MineSweeperStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");
	}

	public void start() {// change to procteted after testing
		primaryStage.show();
	}

}
