package controller;

import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;

import application.Main;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Animations;

public class SideBarController {

	@FXML
	private StackPane stackPane;

	@FXML
	private VBox vBox;

	@FXML
	private Button btnDocument;

	@FXML
	private Button btnInterference;

	Panel contentPanel;



	@FXML
	private void initialize() {
	/*	btnDocument.setOnAction(event -> {
			System.out.println("Open button clicked!");
			//openDocumentView(!contentPanel.isVisible());

		});*/

	}

	private void openDocumentView(Boolean isVisible) {
		contentPanel.setVisible(isVisible);

		if (isVisible) {
			System.out.println(isVisible);
			//loadDocumentView(contentPanel);

			new Animations().animatePanel(contentPanel);

		}

	}

	AtomicReference<AnchorPane> rootRef = new AtomicReference<>();

	/*private void loadDocumentView(Panel contentPanel) {

		// Verifica se já existe um JPanel no contentPanel
		for (Component comp : contentPanel.getComponents()) {
			if (comp instanceof JPanel) {
				System.out.println("JFXPanel já foi adicionado.");

				// Não vai recriar o componente, apenas fazê-lo tremer.
				AnchorPane root = rootRef.get();

				System.out.println("apenas tremer");
				root.setPrefSize(contentPanel.getWidth(), contentPanel.getHeight());

				// Aplica o efeito de tremor
				//new Animations().shake(root);

				return; // Evita adicionar um novo JFXPanel
			}
		}

		JPanel jPanel = new JPanel();
		jPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
		jPanel.revalidate();
		jPanel.repaint();

		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
		jfxPanel.revalidate();
		jfxPanel.repaint();

		URL fxmlPath = Main.class.getClass().getResource("/fxml/Document.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlPath);

		DocumentController controller = new DocumentController(contentPanel);
		loader.setController(controller);

		try {
			rootRef.set(loader.load());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Scene scene = new Scene(rootRef.get());
		jfxPanel.setScene(scene);

		jPanel.add(jfxPanel);
		contentPanel.add(jPanel);

		jPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("map panel update resized");
				jPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
				jfxPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());

				AnchorPane root = rootRef.get();

				if (root != null) {
					root.setPrefSize(contentPanel.getWidth(), contentPanel.getHeight());

					// Aplica o efeito de tremor
					//new Animations().shake(root);
				}

				if (root != null) {
					root.setPrefSize(contentPanel.getWidth(), contentPanel.getHeight());
				}
			}
		});

		contentPanel.revalidate();
		contentPanel.repaint();

	}*/

}
