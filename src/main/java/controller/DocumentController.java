package controller;

import java.awt.Panel;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DocumentController {

	@FXML
	private AnchorPane anchorPane;

	Panel contentPanel;

	public DocumentController(Panel contentPanel) {
		super();
		this.contentPanel = contentPanel;
	}

	@FXML
	private void initialize() {
		TranslateTransition tt = new TranslateTransition(new Duration(3000), anchorPane);

	
		
	}

}
