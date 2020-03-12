package minesweeper;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MineSweeperController {
	
	protected MineSweeperModel model;
	protected MineSweeperView view;
	protected Stage stage;

	protected MineSweeperController(MineSweeperModel model, MineSweeperView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		
		view.aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION, "Text", ButtonType.CLOSE);
			aboutAlert.setTitle("About");
			aboutAlert.setHeaderText("MineSweeper");
			aboutAlert.showAndWait();
		});
		
		view.helpItem.setOnAction(e -> {
			Alert helpAlert = new Alert(AlertType.INFORMATION, "Text", ButtonType.CLOSE);
			helpAlert.setTitle("Help");
			helpAlert.setHeaderText("How to play");
			helpAlert.showAndWait();
		});
		
		view.quitItem.setOnAction(e -> {
			Platform.exit();
		});
		
	
		
		
	}

}
