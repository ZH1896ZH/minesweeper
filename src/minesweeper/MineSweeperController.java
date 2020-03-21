package minesweeper;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MineSweeperController {

	protected MineSweeperModel model;
	protected StackButton stackButton;
	protected MineSweeperView view;
	protected Stage stage;

	protected MineSweeperController(MineSweeperModel model, MineSweeperView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;

		// ActionEvents from MenuBar - they all work !!
		view.aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION,
					"Created by Robin Roth, Robin Weis \nand Luca Schädler\n\n" + "version 0.1", ButtonType.CLOSE);
			aboutAlert.setTitle("About");
			aboutAlert.setHeaderText("MineSweeper");
			aboutAlert.showAndWait();
		});

		view.helpItem.setOnAction(e -> {
			Alert helpAlert = new Alert(AlertType.INFORMATION,
					"The aim of minesweeper is to identify all the sqaures which contain mines.\n\n"
							+ "Left click on a square to reveal a number. This number indicates how many of the adjacent squares contain mines. By using these numbers you can deduce which sqaures contain mines. \n\n"
							+ "Right click on a square to mark it as containing a mine. You can right click the sqaure again to unmark it if you made a mistake.\n\n"
							+ "After all mines have successfully been marked the game is over and you win! Be careful though. Left clicking a square with a mine will result in a game over.",
					ButtonType.CLOSE);
			helpAlert.setTitle("Help");
			helpAlert.setHeaderText("How to play");
			helpAlert.showAndWait();
		});

		view.quitItem.setOnAction(e -> {
			Platform.exit();
		});

		view.smallSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 10;
			model.reload();
		});

		view.mediumSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 15;
			model.reload();
		});

		view.largeSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 20;
			model.reload();
		});

		view.easyItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 10;
			model.reload();
		});

		view.normalItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 15;
			model.reload();
		});

		view.hardItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 20;
			model.reload();
		});

		view.soundOnItem.setOnAction(e -> {
			MineSweeperView.sound = true;
		});

		view.soundOffItem.setOnAction(e -> {
			MineSweeperView.sound = false;
		});

		// audioclip for every mouseclick
		for (int i = 0; i < MineSweeperView.grid.length; i++) {
			for (int j = 0; j < MineSweeperView.grid.length; j++) {// loop works
				MineSweeperView.grid[j][i].setOnMouseClicked(e -> {// referenz zu stackbutton fehlt
					stackButton.onClick(e);
				});

			}
		}

	}
}
