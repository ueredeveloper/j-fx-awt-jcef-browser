package application;

import java.awt.BorderLayout;
import java.awt.Color;
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

import application.controller.DocumentController;
import application.controller.SideBarController;
import application.ui.BrowserPanel;
import application.ui.Controller;
import application.ui.PanelLoader;
import application.ui.SideBarPanelLoader;
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
			

			// MAPA
			Panel mapPanel = new Panel();
			mapPanel.setBounds(0, 0, INITIAL_WIDTH, INITIAL_HEIGHT);
			mapPanel.setLayout(null);

			// Criar o painel do navegador (BrowserPanel) - assumindo que existe
			BrowserPanel browser = new BrowserPanel();
			browser.setBounds(0, 40, mapPanel.getWidth(), mapPanel.getHeight() - 40);
			mapPanel.add(browser);
		

			/* CONTEÚDO SOBRE O MAPA. 
			 * Começa com um awt panel, depois um JPanel Swing, mediando a inicialização de um JFXPanel para o 
			 * conteúdo Javafx.*/
			Panel contentPanel = new Panel();
			contentPanel.setBounds(50, 0, INITIAL_WIDTH - 200, INITIAL_HEIGHT - 140);
			contentPanel.setLayout(null);
			contentPanel.setVisible(false);
			
			
			// Teste de inicialização com a tela de documento aberta
			//Controller documentController = new DocumentController();
			//JPanel panel = PanelLoader.LoadFXML(contentPanel, "Document.fxml", documentController);
			//contentPanel.add(panel, BorderLayout.CENTER);	
			
			
			Controller sideBarController = new SideBarController(contentPanel, browser);
			JPanel sideBarPanel = SideBarPanelLoader.LoadFXML(sideBarController, INITIAL_WIDTH, INITIAL_HEIGHT);
			// Adiciona primerio o side bar e o conteúdo para sobrescrever o mapa		
			
			frame.add(sideBarPanel);
			frame.add(contentPanel);
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
			Panel contentPanel) {

		int yBarSide = (frame.getHeight() - sideBarPanel.getHeight()) / 2;
		int yContent = (frame.getHeight() - contentPanel.getHeight()) / 2;

		mapPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// Está precisando fazer duas vezes para dar certo
		browser.setBounds(0, 40, mapPanel.getWidth() - 8, mapPanel.getHeight() - 50);
		// Faz o mesmo que a linha anterior
		browser.resizeBrowser(mapPanel.getWidth() - 8, mapPanel.getHeight() - 50);

		// Atualizar o painel verde à direita
		sideBarPanel.setBounds(frame.getWidth() - 140, yBarSide, 100, frame.getHeight() - 140);

		contentPanel.setBounds(50, yContent, frame.getWidth() - 200, frame.getHeight() - 140);
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

	

}