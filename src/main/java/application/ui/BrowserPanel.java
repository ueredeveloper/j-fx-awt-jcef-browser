package application.ui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import com.google.gson.Gson;

import application.model.Interferencia;
import application.service.MyHttpServer;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;

public class BrowserPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private CefApp cefApp_;
	private CefClient client_;
	private CefBrowser browser_;

	private MapClickListener listener;

	public void setMapClickListener(MapClickListener listener) {
		this.listener = listener;
	}

	public BrowserPanel() {
		setLayout(new BorderLayout());
		setFocusable(false);
		initializeJCEF();
	}

	private void initializeJCEF() {
		CefAppBuilder builder = new CefAppBuilder();
		builder.setAppHandler(new MavenCefAppHandlerAdapter() {
			@Override
			public void stateHasChanged(CefApp.CefAppState state) {

				if (state == CefApp.CefAppState.TERMINATED) {
					System.out.println("JCEF terminated unexpectedly");
				}
			}
		});

		CefSettings settings = builder.getCefSettings();
		settings.windowless_rendering_enabled = false;
		settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_INFO;

		try {
			cefApp_ = builder.build();

		} catch (Exception e) {
			System.err.println("❌ JCEF build failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		// Create CefClient
		client_ = CefApp.getInstance(settings).createClient();

		int PORT = 8080;

		try {
			new MyHttpServer(PORT);

		} catch (IOException e) {
			System.err.println("❌ HttpServer failed: " + e.getMessage());
			e.printStackTrace();
		}

		String url = "http://localhost:" + PORT + "/html/gmaps/index.html";

		try {
			browser_ = client_.createBrowser(url, false, false); // Off-screen rendering disabled

		} catch (Exception e) {
			System.err.println("❌ Browser creation failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		// (3) Create a simple message router to receive messages from CEF.
		CefMessageRouter msgRouter = CefMessageRouter.create();
		msgRouter.addHandler(new MessageRouterHandler(), true);
		browser_.getClient().addMessageRouter(msgRouter);

		// Make sure the UI updates happen on the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> {
			add(browser_.getUIComponent(), BorderLayout.CENTER); // Add the browser's UI component to the panel
			revalidate(); // Revalidate the panel to reflect the new component
			repaint(); // Repaint the panel to show the updated layout
		});
	}

	public CefBrowser getBrowser() {
		return browser_;
	}

	public void resizeBrowser(int width, int height) {

		// Ensure the width and height are valid
		if (width > 0 && height > 0) {
			// Set the new size for the browser's UI component
			browser_.getUIComponent().setBounds(0, 0, width, height);

			// Revalidate and repaint to update the layout
			revalidate();
			repaint();

		}
	}

	class MessageRouterHandler extends CefMessageRouterHandlerAdapter {

		@Override
		public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request, boolean persistent,
				CefQueryCallback callback) {

			Interferencia interference = new Gson().fromJson(request, Interferencia.class);

			System.out.println(listener == null);
			if (listener != null) {
				listener.onMapClick(interference);
			}

			System.out.println("Received request: " + request);
			callback.success("Response from Java");
			return true;
		}
	}

	public void setInterference(String latitude, String longitude) {
		if (browser_ != null) {
			String script = String.format("window.setInterference('%s', '%s');", latitude, longitude);
			browser_.executeJavaScript(script, browser_.getURL(), 0);
		} else {
			System.err.println("❌ Browser não inicializado.");
		}
	}

}
