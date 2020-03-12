package minesweeper;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MineSweeperView {

	protected Stage primaryStage;
	protected MineSweeperModel model;

	private VBox root = new VBox();
	protected MenuBar menuBar;
	protected Menu fileMenu, sizeMenu, difficultyMenu, soundMenu;
	protected MenuItem aboutItem, helpItem, quitItem, 
					smallSizeItem, mediumSizeItem, largeSizeItem, 
					easyItem, normalItem, hardItem, 
					soundOnItem, soundOffItem;

	
	public MineSweeperView(Stage primaryStage) {//remove after testing the view (start)
		this.primaryStage = primaryStage;
		
		//Menu-Instanziierung
		menuBar = new MenuBar();
		fileMenu = new Menu("File"); sizeMenu = new Menu("Size"); difficultyMenu = new Menu("Difficulty");soundMenu = new Menu("Sound");
		aboutItem = new MenuItem("About"); helpItem = new MenuItem("help"); quitItem = new MenuItem("Quit");
		smallSizeItem = new MenuItem("10x10 (small)"); mediumSizeItem = new MenuItem("15x15 (medium"); largeSizeItem = new MenuItem("20x20 (large)");
		easyItem = new MenuItem("Easy"); normalItem = new MenuItem("Normal"); hardItem = new MenuItem("Hard");
		
		fileMenu.getItems().addAll(aboutItem, helpItem, quitItem);
		soundOnItem = new MenuItem("On"); soundOffItem = new MenuItem("Off");
		sizeMenu.getItems().addAll(smallSizeItem, mediumSizeItem, largeSizeItem);
		difficultyMenu.getItems().addAll(easyItem, normalItem, hardItem);
		soundMenu.getItems().addAll(soundOnItem, soundOffItem);
		
		menuBar.getMenus().addAll(fileMenu,sizeMenu, difficultyMenu, soundMenu);
		
		root.getChildren().add(menuBar);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MineSweeperStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");

	}//(end)


	protected MineSweeperView(Stage primaryStage, MineSweeperModel model) {
		this.primaryStage = primaryStage;
		this.model = model;
		
		//Menu-Instanziierung
				menuBar = new MenuBar();
				fileMenu = new Menu("File"); sizeMenu = new Menu("Size"); difficultyMenu = new Menu("Difficulty");soundMenu = new Menu("Sound");
				aboutItem = new MenuItem("About"); helpItem = new MenuItem("help"); quitItem = new MenuItem("Quit");
				smallSizeItem = new MenuItem("10x10 (small)"); mediumSizeItem = new MenuItem("15x15 (medium"); largeSizeItem = new MenuItem("20x20 (large)");
				easyItem = new MenuItem("Easy"); normalItem = new MenuItem("Normal"); hardItem = new MenuItem("Hard");
				
				fileMenu.getItems().addAll(aboutItem, helpItem, quitItem);
				soundOnItem = new MenuItem("On"); soundOffItem = new MenuItem("Off");
				sizeMenu.getItems().addAll(smallSizeItem, mediumSizeItem, largeSizeItem);
				difficultyMenu.getItems().addAll(easyItem, normalItem, hardItem);
				soundMenu.getItems().addAll(soundOnItem, soundOffItem);
				
				menuBar.getMenus().addAll(fileMenu,sizeMenu, difficultyMenu, soundMenu);
				
				root.getChildren().add(menuBar);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MineSweeperStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MineSweeper");
	}

	public void start() {//change to procteted after testing
		primaryStage.show();
	}

}
