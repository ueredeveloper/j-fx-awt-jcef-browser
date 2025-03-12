package application;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.DocumentController;
import controller.SideBarController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Main {
	public static void main(String[] args) {
		// Inicializar o Swing na EDT
		SwingUtilities.invokeLater(() -> {
			// Criar a janela principal
			Frame frame = new Frame("Exemplo de Painéis sobrepostos");
			frame.setLayout(null); // Usar layout nulo para controle total

			final int INITIAL_WIDTH = 1920; // Largura inicial
			final int INITIAL_HEIGHT = 1080; // Altura inicial
		
			JPanel sideBarPanel = loadSideBar(INITIAL_WIDTH, INITIAL_HEIGHT);

			frame.add(sideBarPanel);

			JPanel contentPanel = loadDocument(INITIAL_WIDTH, INITIAL_HEIGHT);
			frame.add(contentPanel);

			// Criar painel azul de fundo
			Panel mapPanel = new Panel();
			mapPanel.setBounds(0, 0, INITIAL_WIDTH, INITIAL_HEIGHT);
			mapPanel.setLayout(null);

			// Criar o painel do navegador (BrowserPanel) - assumindo que existe
			BrowserPanel browser = new BrowserPanel();
			browser.setBounds(0, 40, mapPanel.getWidth(), mapPanel.getHeight() - 40);
			mapPanel.add(browser);
			frame.add(mapPanel);

			// Definir tamanho da janela
			frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
			frame.setVisible(true);

			// Listener para fechar a janela
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					System.exit(0);
				}
			});

			// Listener para redimensionamento do frame
			frame.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					
					updatePanels(frame, mapPanel, sideBarPanel, browser, contentPanel);
				}
			});

			// Listener para redimensionamento do mapPanel
			mapPanel.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					
					updatePanels(frame, mapPanel, sideBarPanel, browser, contentPanel);
				}
			});

		});

	}

	private static void updatePanels(Frame frame, Panel mapPanel, JPanel sideBarPanel, BrowserPanel browser,
			JPanel contentPanel) {

		int yBarSide = (frame.getHeight() - sideBarPanel.getHeight()) / 2;
		int yContent = (frame.getHeight() - contentPanel.getHeight()) / 2;

		mapPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// Está precisando fazer duas vezes para dar certo
		browser.setBounds(0, 40, mapPanel.getWidth() - 8, mapPanel.getHeight() - 50);
		// Faz o mesmo que a linha anterior
		browser.resizeBrowser(mapPanel.getWidth() - 8, mapPanel.getHeight() - 50);

		// Atualizar o painel verde à direita
		sideBarPanel.setBounds(frame.getWidth() - 140, yBarSide, 100, frame.getHeight() - 100);

		contentPanel.setBounds(50, yContent, frame.getWidth() - 200, frame.getHeight() - 100);
	}

	private static JPanel loadSideBar(int INITIAL_WIDTH, int INITIAL_HEIGHT) {

		// Criar o JFXPanel para JavaFX
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(INITIAL_WIDTH - 140, (INITIAL_HEIGHT - 400) / 2, 100, INITIAL_HEIGHT - 100);// Ajuste
					
		// O JPanel do Swing gerencia o JFXPanel
		JPanel jPanel = new JPanel();
		jPanel.setBounds(INITIAL_WIDTH - 140, (INITIAL_HEIGHT - 400) / 2, 100, INITIAL_HEIGHT - 100);
		jPanel.setLayout(new BorderLayout()); // Usar BorderLayout para o JFXPanel
		jPanel.add(jfxPanel, BorderLayout.CENTER);

		// Listener para redimensionamento do mapPanel
		jfxPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				jfxPanel.setBounds(0, 0, jPanel.getWidth(), jPanel.getHeight());
				jfxPanel.revalidate();
				jfxPanel.repaint();
			}
		});

		Platform.runLater(() -> {
			// Carregar o conteúdo JavaFX na thread correta

			try {
				// Carregar o arquivo FXML
				URL fxmlPath = Main.class.getResource("/fxml/SideBar.fxml");
				if (fxmlPath == null) {
					throw new IOException("FXML file not found");
				}

				FXMLLoader loader = new FXMLLoader(fxmlPath);
				SideBarController controller = new SideBarController();
				loader.setController(controller);

				StackPane root = loader.load();
				jfxPanel.setScene(new javafx.scene.Scene(root));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Forçar revalidação do JPanel após adicionar o JFXPanel
		jPanel.revalidate();
		jPanel.repaint();

		return jPanel;

	}

	private static void loadLogin(Panel contentPanel) {
		// Criar um JFXPanel para embutir o JavaFX dentro de AWT
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(100, 100, 50, 50); // Ajuste conforme necessário

		StackPane root = new StackPane();

		// Carregar o arquivo FXML
		URL fxmlPath = null;
		try {
			fxmlPath = Main.class.getClass().getResource("/fxml/Login.fxml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		FXMLLoader loader = new FXMLLoader(fxmlPath);

		// Passar o controlador com os parâmetros para o FXMLLoader
		// LoginController controller = new LoginController(contentPanel);
		// loader.setController(controller);

		try {
			root = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// O JPanel do swing gerencia melhor o jfxPanel
		JPanel jPanel = new JPanel();
		jPanel.setBounds(100, 100, 50, 50);
		jPanel.revalidate();

		jPanel.revalidate();
		jPanel.repaint();

		jPanel.add(jfxPanel);

		jfxPanel.revalidate();
		jfxPanel.repaint();

		// Configurar a cena e o palco
		Scene scene = new Scene(root);
		jfxPanel.setScene(scene);
		contentPanel.add(jPanel);
	}

	private static JPanel loadDocument(int INITIAL_WIDTH, int INITIAL_HEIGHT) {

		// Criar o JFXPanel para JavaFX
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(100, 100, 50, 50); // Ajuste conforme necessário

		// O JPanel do Swing gerencia o JFXPanel
		JPanel jPanel = new JPanel();
		jPanel.setBounds(50, (INITIAL_HEIGHT - 400), INITIAL_WIDTH - 200, INITIAL_HEIGHT - 100);
		jPanel.setLayout(new BorderLayout()); // Usar BorderLayout para o JFXPanel
		jPanel.add(jfxPanel, BorderLayout.CENTER);

		// Listener para redimensionamento do mapPanel
		jfxPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				jfxPanel.setBounds(0, 0, jPanel.getWidth(), jPanel.getHeight());
				jfxPanel.revalidate();
				jfxPanel.repaint();
			}
		});

		Platform.runLater(() -> {
			// Carregar o conteúdo JavaFX na thread correta

			try {
				// Carregar o arquivo FXML
				URL fxmlPath = Main.class.getResource("/fxml/Document.fxml");
				if (fxmlPath == null) {
					throw new IOException("FXML file not found");
				}

				FXMLLoader loader = new FXMLLoader(fxmlPath);
				DocumentController controller = new DocumentController();
				loader.setController(controller);

				StackPane root = loader.load();
				jfxPanel.setScene(new javafx.scene.Scene(root));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Forçar revalidação do JPanel após adicionar o JFXPanel
		jPanel.revalidate();
		jPanel.repaint();

		return jPanel;

	}

}