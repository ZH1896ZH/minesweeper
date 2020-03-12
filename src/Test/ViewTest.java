package Test;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.MineSweeperView;

public class ViewTest extends Application {

	private MineSweeperView view;

	public void start(Stage primaryStage) throws Exception {
		this.view = new MineSweeperView(primaryStage);

		view.start();
	}

	public static void main(String[] args) {
		launch();
	}

}
