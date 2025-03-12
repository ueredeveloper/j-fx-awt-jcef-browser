package application.ui;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class SideBarPanelLoader {

	public static JPanel LoadFXML(Controller controller, int INITIAL_WIDTH, int INITIAL_HEIGHT) {
		JPanel jPanel = new JPanel();
		jPanel.setBounds(INITIAL_WIDTH - 140, (INITIAL_HEIGHT - 400) / 2, 100, INITIAL_HEIGHT - 100);
		jPanel.setLayout(new BorderLayout());

		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(INITIAL_WIDTH - 140, (INITIAL_HEIGHT - 400) / 2, 100, INITIAL_HEIGHT - 100);// Ajuste
		jPanel.add(jfxPanel, BorderLayout.CENTER);

		// Redimensionamento do JFXPanel
		jPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				jfxPanel.setBounds(0, 0, jPanel.getWidth(), jPanel.getHeight());
				jfxPanel.revalidate();
				jfxPanel.repaint();
			}
		});

		Platform.runLater(() -> {
			try {
				URL fxmlUrl = PanelLoader.class.getResource("/fxml/SideBar.fxml");
				if (fxmlUrl == null) {
					throw new IOException("FXML file not found: " + "/fxml/SideBar.fxml");
				}

				FXMLLoader loader = new FXMLLoader(fxmlUrl);
				loader.setController(controller);
				StackPane root = loader.load();
				Scene scene = new Scene(root, 800, 600);
				jfxPanel.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		jPanel.revalidate();
		jPanel.repaint();
		return jPanel;
	}
}