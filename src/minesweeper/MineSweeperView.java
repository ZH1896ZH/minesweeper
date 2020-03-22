package minesweeper;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MineSweeperView {

	// MVC-branch
	protected static Stage primaryStage;
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
	protected static Image mine = new Image("resources/mine.png");
	
	protected GridPane infoBar = new GridPane();
	protected Label timeLabel,bombsFoundLabel, bombsLeftLabel;
	protected TextField timeField, bombsFoundField, bombsLeftField;
	protected String text;
	protected ChangeListener changeListener;
	


	protected MineSweeperView(Stage primaryStage, MineSweeperModel model) {
		MineSweeperView.primaryStage = primaryStage;
		this.model = model;

		// Menu-Instanziierung
		menuBar = new MenuBar(); fileMenu = new Menu("File");
		sizeMenu = new Menu("Size"); difficultyMenu = new Menu("Difficulty");
		soundMenu = new Menu("Sound"); aboutItem = new MenuItem("About");
		helpItem = new MenuItem("Help"); quitItem = new MenuItem("Quit");
		smallSizeItem = new MenuItem("Small (10x10)"); mediumSizeItem = new MenuItem("Medium (15x15)");
		largeSizeItem = new MenuItem("Large (20x20)"); easyItem = new MenuItem("Easy (10% Bombs)");
		normalItem = new MenuItem("Normal (15% Bombs)"); hardItem = new MenuItem("Hard (20% Bombs)");
		soundOnItem = new RadioMenuItem("On"); soundOffItem = new RadioMenuItem("Off");

		fileMenu.getItems().addAll(aboutItem, helpItem, quitItem);
		sizeMenu.getItems().addAll(smallSizeItem, mediumSizeItem, largeSizeItem);
		difficultyMenu.getItems().addAll(easyItem, normalItem, hardItem);
		soundMenu.getItems().addAll(soundOnItem, soundOffItem);

		ToggleGroup soundToggle = new ToggleGroup();
		soundToggle.getToggles().addAll(soundOnItem, soundOffItem);
		soundToggle.selectToggle(soundOnItem);

		menuBar.getMenus().addAll(fileMenu, sizeMenu, difficultyMenu, soundMenu);
		
		//infoBar instanziierung
		timeLabel = new Label("Time");
		timeField = new TextField(Integer.toString(secondsPassed));
		bombsFoundLabel = new Label("Bombs found");
		bombsFoundField = new TextField(Integer.toString(foundBombs));
		bombsLeftLabel = new Label("Bombs left");
		bombsLeftField = new TextField(Integer.toString(numBombs - foundBombs));
		
		
		timeField.textProperty().addListener(//müemmer no fixe
				
				(observable, oldValue, newValue) -> {
				newValue = Integer.toString(secondsPassed);
				});
		
		infoBar.add(timeLabel, 0 ,0);
		infoBar.add(timeField, 1, 0);
		infoBar.add(bombsFoundLabel, 0, 1);
		infoBar.add(bombsFoundField, 1, 1);
		infoBar.add(bombsLeftLabel, 0, 2);
		infoBar.add(bombsLeftField, 1, 2);

		// MenuBar, InfoBar, Grid werden der VBox(root) hinzugefügt
		root.getChildren().addAll(menuBar, infoBar, MineSweeperModel.createContent());


		// Szene instanziieren und an Stage weitergeben
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");
		primaryStage.getIcons().add(mine);
	}


	protected void start() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				secondsPassed++;
			};
		};
		timer = new Timer();
		timer.schedule(task, 1000, 1000);

		primaryStage.show();
	}

	protected Stage getStage() {
		return primaryStage;
	}

}
