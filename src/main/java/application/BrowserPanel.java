package application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.CefSettings;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import service.MyHttpServer;

public class BrowserPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private CefApp cefApp_;
    private CefClient client_;
    private CefBrowser browser_;

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
                System.out.println("🔄 JCEF state changed: " + state);
                if (state == CefApp.CefAppState.TERMINATED) {
                    System.out.println("⚠️ JCEF terminated unexpectedly");
                }
            }
        });

        CefSettings settings = builder.getCefSettings();
        settings.windowless_rendering_enabled = false;
        settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_INFO;

        try {
            cefApp_ = builder.build();
            System.out.println("✅ JCEF initialized successfully");
        } catch (Exception e) {
            System.err.println("❌ JCEF build failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Create CefClient
        client_ = CefApp.getInstance(settings).createClient();
        System.out.println("✅ CefClient created");

        int PORT = 8080;

        try {
            new MyHttpServer(PORT);
            System.out.println("✅ HttpServer started on port " + PORT);
        } catch (IOException e) {
            System.err.println("❌ HttpServer failed: " + e.getMessage());
            e.printStackTrace();
        }

        String url = "http://localhost:" + PORT + "/html/gmaps/index.html";

        try {
            browser_ = client_.createBrowser(url, false, false); // Off-screen rendering disabled

            System.out.println("✅ CefBrowser created");
        } catch (Exception e) {
            System.err.println("❌ Browser creation failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Make sure the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            add(browser_.getUIComponent(), BorderLayout.CENTER);  // Add the browser's UI component to the panel
            revalidate(); // Revalidate the panel to reflect the new component
            repaint();    // Repaint the panel to show the updated layout
        });
    }

    public CefBrowser getBrowser() {
        return browser_;
    }
    public void resizeBrowser(int width, int height) {
    	
    	System.out.println("browser resize browser " + width + " " + height);
        // Ensure the width and height are valid
        if (width > 0 && height > 0) {
            // Set the new size for the browser's UI component
            //browser_.getUIComponent().setSize(width, height);
            browser_.getUIComponent().setBounds(0,0,width, height);
            
            // Revalidate and repaint to update the layout
            revalidate();
            repaint();
         
        }
    }

}
