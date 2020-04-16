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

public class Controller {

	protected Model model;
	protected StackButton stackButton;
	protected View view;
	protected Stage stage;

	protected Controller(Model model, View view, Stage stage) {
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
			View.gridSize = 10;
			Model.reload();
		});

		view.mediumSizeItem.setOnAction(e -> {
			View.gridSize = 15;
			Model.reload();
		});

		view.largeSizeItem.setOnAction(e -> {
			View.gridSize = 20;
			Model.reload();
		});

		view.easyItem.setOnAction(e -> {
			View.bombPercent = 10;
			Model.reload();
		});

		view.normalItem.setOnAction(e -> {
			View.bombPercent = 15;
			Model.reload();
		});

		view.hardItem.setOnAction(e -> {
			View.bombPercent = 20;
			Model.reload();
		});

		view.soundOnItem.setOnAction(e -> {
			View.sound = true;
		});

		view.soundOffItem.setOnAction(e -> {
			View.sound = false;
		});

		//setOnCloseRequest
		this.view.getStage().setOnCloseRequest(e -> {
			
			Stage newStage = new Stage();
			VBox root = new VBox();
			Button yesBtn = new Button("Ja");
			Button noBtn = new Button("Nein");
			HBox hbox = new HBox();
			hbox.getChildren().addAll(yesBtn, noBtn);
			root.getChildren().addAll(new Label("Bist du sicher, dass du Minesweeper beenden willst?"), hbox);

			//Layout
			hbox.setSpacing(20);
			hbox.setMinHeight(40);
			hbox.setAlignment(Pos.CENTER);
			root.setSpacing(5);
			root.setAlignment(Pos.CENTER);
	
			//Stage to scene
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/resources/closeRequest.css").toExternalForm());
			newStage.setScene(scene);
			newStage.getIcons().add(View.mine);
			newStage.setTitle("Bestätigung");
			newStage.show();

			//Button ActionEvents
			yesBtn.setOnAction(event -> {
				Platform.exit();
				System.exit(0);
			});
			noBtn.setOnAction(evente -> {
				stage.show();
				newStage.close();
			});
			
			newStage.setOnCloseRequest(ev ->{
				Platform.exit();
				System.exit(0);
			});

		});
		//Listener for Timer
		view.getSecondsPassedProperty().addListener((observable, oldValue, newValue) -> {
			DecimalFormat fmtt = new DecimalFormat("#0");
			DecimalFormat fmt = new DecimalFormat("00");
			String newText = (fmtt.format(View.minutesPassedObj.get()) + ":"
					+ fmt.format(View.secondsPassedObj.get()));

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.timeField.setText(newText);
				}
			});
		});
	}
}
