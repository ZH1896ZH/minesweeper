package minesweeper;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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

		// MenuBar ActionEvents
		view.aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION,
					"Erstellt von Robin Roth, Robin Weis \nand Luca Schädler\n\n" + "Version 1.1", ButtonType.CLOSE);
			aboutAlert.setTitle("Über");
			aboutAlert.setHeaderText("Minesweeper");
			aboutAlert.showAndWait();
		});

		view.helpItem.setOnAction(e -> {
			Alert helpAlert = new Alert(AlertType.INFORMATION,
					"Auf den ersten Blick ist Minesweeper ein einfaches Denk- und Logikspiel.\n" + "\n"
							+ "Das Ziel: Der Spieler muss die leeren Felder aufdecken und dabei diejenigen Felder meiden, hinter denen sich Minen verstecken.\n" + "\n"
							+ "Das Spiel ist normalerweise beendet, wenn eine Mine aufgedeckt wird. Im Gegensatz zum Windows-Minesweeper ist durch eine Zugrücknahme ein Weiterspielen möglich. Die aufgedeckten Minen werden in einem Zählfeld angezeigt\n"
							+ "\n" + "Das Spiel wird fortgesetzt, wenn Sie ein leeres Feld aufdecken.\n" + "\n"
							+ "Wird beim Aufdecken eines Feldes eine Zahl angezeigt, steht diese für die Anzahl der Minen, die in den benachbarten 8 Feldern verborgen sind. Anhand dieser Angabe kann abgeleitet werden, unter welchen der angrenzenden Feldern sich Minen befinden und auf welche Felder gefahrlos geklickt werden kann.",
					ButtonType.CLOSE);
			helpAlert.setTitle("Hilfe");
			helpAlert.setHeaderText("Spielregeln");
			helpAlert.showAndWait();
		});

		view.quitItem.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		view.smallSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 10;
			MineSweeperModel.reload();
		});

		view.mediumSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 15;
			MineSweeperModel.reload();
		});

		view.largeSizeItem.setOnAction(e -> {
			MineSweeperView.gridSize = 20;
			MineSweeperModel.reload();
		});

		view.easyItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 10;
			MineSweeperModel.reload();
		});

		view.normalItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 15;
			MineSweeperModel.reload();
		});

		view.hardItem.setOnAction(e -> {
			MineSweeperView.bombPercent = 20;
			MineSweeperModel.reload();
		});

		view.soundOnItem.setOnAction(e -> {
			MineSweeperView.sound = true;
		});

		view.soundOffItem.setOnAction(e -> {
			MineSweeperView.sound = false;
		});

		// setOnCloseRequest
		this.view.getStage().setOnCloseRequest(e -> {

			// new stage
			Stage newStage = new Stage();
			VBox root = new VBox();

			Label lbl = new Label("Bist du sicher, dass du es Beenden willst?");
			Button yes = new Button("Ja");
			Button no = new Button("Nein");

			HBox hbox = new HBox();

			// fill stage
			hbox.getChildren().addAll(yes, no);
			root.getChildren().addAll(lbl, hbox);

			// layout
			hbox.setSpacing(10);
			hbox.setAlignment(Pos.CENTER);

			root.setAlignment(Pos.CENTER);
			root.setSpacing(10);

			// stage to scene
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/resources/CloseRequest.css").toExternalForm());
			newStage.setScene(scene);
			newStage.getIcons().add(MineSweeperView.mine);
			newStage.show();

			// Button action
			yes.setOnAction(event -> {
				Platform.exit();
				System.exit(0);
			});
			no.setOnAction(evente -> {
				stage.show();
				newStage.close();
			});

		});

		view.getSecondsPassedProperty().addListener((observable, oldValue, newValue) -> {
			DecimalFormat fmtt = new DecimalFormat("#0");
			DecimalFormat fmt = new DecimalFormat("00");
			String newText = (fmtt.format(MineSweeperView.minutesPassedObj.get()) + ":"
					+ fmt.format(MineSweeperView.secondsPassedObj.get()));

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.timeField.setText(newText);
				}
			});
		});
	}
}
