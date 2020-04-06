package minesweeper;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.SimpleIntegerProperty;
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

public class View {

	// MVC-branch
	protected static Stage stage;
	protected Model model;

	// game elements
	protected static boolean sound = true;
	protected static Timer timer;
	protected TimerTask task;

	protected static SimpleIntegerProperty secondsPassedObj;
	protected static SimpleIntegerProperty minutesPassedObj;
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
	protected Label timeLabel, timeField;
	protected TextField bombsFoundField, bombsLeftField;

	protected View(Stage primaryStage, Model model) {
		View.stage = primaryStage;
		this.model = model;

		// Menu-Instanziierung
		menuBar = new MenuBar();
		fileMenu = new Menu("Datei");
		sizeMenu = new Menu("Grösse");
		difficultyMenu = new Menu("Schwierigkeit");
		soundMenu = new Menu("Sound");
		aboutItem = new MenuItem("Über");
		helpItem = new MenuItem("Hilfe");
		quitItem = new MenuItem("Beenden");
		smallSizeItem = new MenuItem("Klein (10x10)");
		mediumSizeItem = new MenuItem("Mittel (15x15)");
		largeSizeItem = new MenuItem("Gross (20x20)");
		easyItem = new MenuItem("Einfach (10% Bombs)");
		normalItem = new MenuItem("Normal (15% Bombs)");
		hardItem = new MenuItem("Schwer (20% Bombs)");
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
		
		// infoBar instanziierung
		timeLabel = new Label("Time: ");
		timeField = new Label();
		secondsPassedObj = new SimpleIntegerProperty();
		minutesPassedObj = new SimpleIntegerProperty();

		infoBar.add(timeLabel, 0, 0);
		infoBar.add(timeField, 1, 0);
		infoBar.setId("infoBar");

		// MenuBar, InfoBar, Grid werden der VBox(root) hinzugefügt
		root.getChildren().addAll(menuBar, infoBar, Model.createContent());// infoBar ev neu gestalten

		// Szene instanziieren und an Stage weitergeben
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/resources/Minesweeper.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.sizeToScene();
		primaryStage.resizableProperty().setValue(Boolean.FALSE);// kann man nicht maximimieren
		primaryStage.setTitle("MineSweeper");
		primaryStage.getIcons().add(mine);
	}

	protected void start() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				secondsPassedObj.set(secondsPassedObj.get() + 1);
				if (secondsPassedObj.get() == 60) {
					secondsPassedObj.set(0);
					minutesPassedObj.set(minutesPassedObj.get() + 1);
				}

			};
		};
		timer = new Timer();
		timer.schedule(task, 1000, 1000);

		stage.show();
	}

	protected Stage getStage() {
		return stage;
	}

	public SimpleIntegerProperty getSecondsPassedProperty() {
		return secondsPassedObj;
	}
	
	public SimpleIntegerProperty getMinutesPassedProperty() {
		return minutesPassedObj;
	}



}
