package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;

public class MVC extends Application {

	protected Model model;
	protected Controller controller;
	protected View view;

	//MVC-Modell
	public void start(Stage primaryStage) throws Exception {
		model = new Model(primaryStage);
		view = new View(primaryStage, model);
		controller = new Controller(model, view, primaryStage);
		
	//startmethode in view	
		view.start();
	}
	
	
	
	public static void main(String[] args) {
		launch();

	}
}
