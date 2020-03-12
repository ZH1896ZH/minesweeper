package minesweeper;

import javafx.scene.control.Alert;
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
			helptAlert.setHeaderText("How to play");
			helptAlert.showAndWait();
		});
		
		view.quitItem.setOnAction(e -> {
			Platform.exit();
		});
		
		view.MenuItem.setOnAction(e -> {
			MenuItem item = (MenuItem) e.getSource();
			
			if(item == view.smallSizeItem)
				model.setSize(10);
			
			
		});
		
		
	}

}
