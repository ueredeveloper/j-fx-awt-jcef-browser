package application.controller;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;

import com.jfoenix.controls.JFXButton;

import application.model.Interferencia;
import application.ui.BrowserPanel;
import application.ui.Controller;
import application.ui.PanelLoader;
import application.utils.Animations;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SideBarController implements Controller {

	@FXML
	private StackPane stackPane;

	@FXML
	private VBox vBox;

	@FXML
	private JFXButton btnMap;

	@FXML
	private JFXButton btnDocument;

	@FXML
	private JFXButton btnConfig;

	Panel contentPanel;
	BrowserPanel browser;
	
	public SideBarController(Panel contentPanel, BrowserPanel browser) {
		super();
		this.contentPanel = contentPanel;
		this.browser = browser;
	}

	@FXML
	private void initialize() {
		btnMap.setOnAction(e -> {
			
			contentPanel.setVisible(!contentPanel.isVisible());
			
			
			
		});
		
		btnDocument.setOnAction(e -> {
		    contentPanel.setVisible(!contentPanel.isVisible());

		    if (contentPanel.isVisible()) {
		        boolean hasPanel = false;

		        for (java.awt.Component component : contentPanel.getComponents()) {
		            if (component instanceof JPanel) {
		                hasPanel = true;
		                break;
		            }
		        }

		        if (!hasPanel) {
		            Controller documentController = new DocumentController(browser);

		            JPanel panel = PanelLoader.LoadFXML(contentPanel, "Document.fxml", documentController);
		            contentPanel.add(panel, BorderLayout.CENTER);
		        }
		    } else {
		        System.out.println("conteúdo não visível");
		    }
		});


	}

	private void openDocumentView(Boolean isVisible) {
		contentPanel.setVisible(isVisible);

		if (isVisible) {
			System.out.println(isVisible);
			// loadDocumentView(contentPanel);

			new Animations().animatePanel(contentPanel);

		}

	}

	AtomicReference<AnchorPane> rootRef = new AtomicReference<>();

	@Override
	public void updateCoordinates(Interferencia interference) {
		// TODO Auto-generated method stub
		
	}



}
