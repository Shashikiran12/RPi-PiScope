package com.piscope;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PiPreferenceController {

	public PiPreferenceController() {
		// TODO Auto-generated constructor stub
	}

	private String message;

	@FXML
	private Label messageLabel;

	@FXML
	void initialize() {
		messageLabel.setText(message);
	}

	public PiPreferenceController(String message) {
		this.message = message;
	}

	void dialogBuild() throws IOException {
		Stage dialogStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"PiPreference.fxml"));

		loader.setController(new PiPreferenceController(""));
		BorderPane root = (BorderPane) loader.load();
		// PiPreferenceController controller = (PiPreferenceController)
		// loader.getController();
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}

	@FXML
	public void close() {
		messageLabel.getScene().getWindow().hide();
	}

}
