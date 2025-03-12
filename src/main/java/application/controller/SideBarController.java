package application.controller;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;

import com.jfoenix.controls.JFXButton;

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
	
	public SideBarController(Panel contentPanel) {
		super();
		this.contentPanel = contentPanel;
	}

	@FXML
	private void initialize() {
		btnMap.setOnAction(e -> {
			
			contentPanel.setVisible(!contentPanel.isVisible());
			
			if (contentPanel.isVisible()) {
				Controller documentController = new DocumentController();
				JPanel panel = PanelLoader.LoadFXML(contentPanel, "Document.fxml", documentController);

				contentPanel.add(panel, BorderLayout.CENTER);
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

	/*
	 * private void loadDocumentView(Panel contentPanel) {
	 * 
	 * // Verifica se já existe um JPanel no contentPanel for (Component comp :
	 * contentPanel.getComponents()) { if (comp instanceof JPanel) {
	 * System.out.println("JFXPanel já foi adicionado.");
	 * 
	 * // Não vai recriar o componente, apenas fazê-lo tremer. AnchorPane root =
	 * rootRef.get();
	 * 
	 * System.out.println("apenas tremer");
	 * root.setPrefSize(contentPanel.getWidth(), contentPanel.getHeight());
	 * 
	 * // Aplica o efeito de tremor //new Animations().shake(root);
	 * 
	 * return; // Evita adicionar um novo JFXPanel } }
	 * 
	 * JPanel jPanel = new JPanel(); jPanel.setBounds(0, 0, contentPanel.getWidth(),
	 * contentPanel.getHeight()); jPanel.revalidate(); jPanel.repaint();
	 * 
	 * JFXPanel jfxPanel = new JFXPanel(); jfxPanel.setBounds(0, 0,
	 * contentPanel.getWidth(), contentPanel.getHeight()); jfxPanel.revalidate();
	 * jfxPanel.repaint();
	 * 
	 * URL fxmlPath = Main.class.getClass().getResource("/fxml/Document.fxml");
	 * FXMLLoader loader = new FXMLLoader(fxmlPath);
	 * 
	 * DocumentController controller = new DocumentController(contentPanel);
	 * loader.setController(controller);
	 * 
	 * try { rootRef.set(loader.load()); } catch (IOException e1) {
	 * e1.printStackTrace(); }
	 * 
	 * Scene scene = new Scene(rootRef.get()); jfxPanel.setScene(scene);
	 * 
	 * jPanel.add(jfxPanel); contentPanel.add(jPanel);
	 * 
	 * jPanel.addComponentListener(new ComponentAdapter() {
	 * 
	 * @Override public void componentResized(ComponentEvent e) {
	 * System.out.println("map panel update resized"); jPanel.setBounds(0, 0,
	 * contentPanel.getWidth(), contentPanel.getHeight()); jfxPanel.setBounds(0, 0,
	 * contentPanel.getWidth(), contentPanel.getHeight());
	 * 
	 * AnchorPane root = rootRef.get();
	 * 
	 * if (root != null) { root.setPrefSize(contentPanel.getWidth(),
	 * contentPanel.getHeight());
	 * 
	 * // Aplica o efeito de tremor //new Animations().shake(root); }
	 * 
	 * if (root != null) { root.setPrefSize(contentPanel.getWidth(),
	 * contentPanel.getHeight()); } } });
	 * 
	 * contentPanel.revalidate(); contentPanel.repaint();
	 * 
	 * }
	 */

}
