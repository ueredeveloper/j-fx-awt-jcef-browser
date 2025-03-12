package application.ui;

import java.awt.BorderLayout;
import java.awt.Panel;
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


public class PanelLoader {

    public static JPanel LoadFXML (Panel contentPanel, String fxmlPath, Controller controller) {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
        jPanel.setLayout(new BorderLayout());

        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
        jPanel.add(jfxPanel, BorderLayout.CENTER);

        // Redimensionamento do JPanel
        contentPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("content resized: " + contentPanel.getWidth());
                jPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
                jPanel.revalidate();
                jPanel.repaint();
            }
        });

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
                URL fxmlUrl = PanelLoader.class.getResource("/fxml/" + fxmlPath);
                if (fxmlUrl == null) {
                    throw new IOException("FXML file not found: " + fxmlPath);
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