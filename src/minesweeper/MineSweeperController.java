package minesweeper;

import javafx.stage.Stage;

public class MineSweeperController {
	
	protected MineSweeperModel model;
	protected MineSweeperView view;
	protected Stage stage;

	protected MineSweeperController(MineSweeperModel model, MineSweeperView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
	}

}
