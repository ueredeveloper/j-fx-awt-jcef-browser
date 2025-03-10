package application;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.Timer;

import javax.swing.JPanel;

import controller.SideBarController;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Main {
	public static void main(String[] args) {
		// Criar a janela principal
		Frame frame = new Frame("Exemplo de Painéis sobrepostos");
		frame.setLayout(null); // Usar layout nulo para controle total

		final int INITIAL_WIDTH = 1024; // Largura inicial
		final int INITIAL_HEIGHT = 768; // Altura inicial

		// Criar painel verde no topo
		Panel barSidePanel = new Panel();
		// barSidePanel.setBackground(Color.GREEN);
		barSidePanel.setBounds(INITIAL_WIDTH - 100, (INITIAL_HEIGHT - 400) / 2, 80, INITIAL_HEIGHT - 100);
		frame.add(barSidePanel);

		Panel contentPanel = new Panel();
		// contentPanel.setBackground(Color.ORANGE);
		contentPanel.setBounds(50, (INITIAL_HEIGHT - 400), INITIAL_WIDTH - 200, INITIAL_HEIGHT - 100);
		contentPanel.setVisible(false);
		contentPanel.setFocusable(false);

		frame.add(contentPanel);

		// Criar painel azul de fundo
		Panel mapPanel = new Panel();
		// mapPanel.setBackground(Color.BLUE);
		mapPanel.setBounds(0, 0, INITIAL_WIDTH, INITIAL_HEIGHT);
		mapPanel.setLayout(null);

		// Criar o painel do navegador (BrowserPanel)
		BrowserPanel browser = new BrowserPanel();
		browser.setBounds(0, 30, mapPanel.getWidth(), mapPanel.getHeight() - 35);

		mapPanel.add(browser);

		frame.add(mapPanel);

		barSidePanel.add(loadInitialJavaFx(frame, barSidePanel, contentPanel));

		// Definir tamanho da janela
		frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0); // Fecha a aplicação ao clicar no botão de fechar
			}

		});

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				System.out.println("update resized");
				updatePanels(frame, mapPanel, barSidePanel, browser, contentPanel);
			}
		});

		// Atualiza quando o panel do mapa é atualizado
		mapPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("map panel update resized");
				updatePanels(frame, mapPanel, barSidePanel, browser, contentPanel);
			}
		});

	}

	private static void updatePanels(Frame frame, Panel mapPanel, Panel barSidePanel, BrowserPanel browser,
			Panel contentPanel) {

		int yBarSide = (frame.getHeight() - barSidePanel.getHeight()) / 2;
		int yContent = (frame.getHeight() - contentPanel.getHeight()) / 2;

		mapPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// Está precisando fazer duas vezes para dar certo
		browser.setBounds(0, 30, mapPanel.getWidth() - 8, mapPanel.getHeight() - 35);
		// Faz o mesmo que a linha anterior
		browser.resizeBrowser(mapPanel.getWidth() - 8, mapPanel.getHeight() - 35);

		// Atualizar o painel verde à direita
		barSidePanel.setBounds(frame.getWidth() - 100, yBarSide, 80, frame.getHeight() - 100);

		contentPanel.setBounds(50, yContent, frame.getWidth() - 200, frame.getHeight() - 100);
	}

	private static JPanel loadInitialJavaFx(Frame frame, Panel barSidePanel, Panel contentPanel) {
		// Criar um JFXPanel para embutir o JavaFX dentro de AWT
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(100, 100, 50, 50); // Ajuste conforme necessário

		StackPane root = new StackPane();

		// Carregar o arquivo FXML
		URL fxmlPath = null;
		try {
			fxmlPath = Main.class.getClass().getResource("/fxml/SideBar.fxml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		FXMLLoader loader = new FXMLLoader(fxmlPath);

		// Passar o controlador com os parâmetros para o FXMLLoader
		SideBarController sideBarController = new SideBarController(contentPanel);
		loader.setController(sideBarController);

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
		return jPanel;
	}
	


}