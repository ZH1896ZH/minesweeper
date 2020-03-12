package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;

public class MineSweeperMVC extends Application {

	protected MineSweeperModel model;
	protected MineSweeperController controller;
	protected MineSweeperView view;
	protected Stage stage;
	

	public void start(Stage primaryStage) throws Exception {
		model = new MineSweeperModel();
		view = new MineSweeperView(stage, model);
		controller = new MineSweeperController(model, view, stage);
		
		view.start();
	}
	
	
	
	public static void main(String[] args) {
		launch();

	}
}
