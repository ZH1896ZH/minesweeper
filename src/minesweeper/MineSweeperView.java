package minesweeper;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MineSweeperView {

	protected Stage stage;
	protected MineSweeperModel model;

	private VBox root = new VBox();
	
	public MineSweeperView(Stage stage) {//remove after testing the view (start)
		this.stage = stage;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MineSweeperStyle.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("MineSweeper");
	}//(end)

	protected MineSweeperView(Stage stage, MineSweeperModel model) {
		this.stage = stage;
		this.model = model;

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MineSweeperStyle.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("MineSweeper");
	}

	public void start() {//change to procteted after testing
		stage.show();
	}

}
