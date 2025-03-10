package application;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ContentPanel extends JFXPanel {
    private static final long serialVersionUID = 1L;
    private WritableImage writableImage;
    private ImageView imageView;

    public ContentPanel(int frameWidth, int frameHeight) {
        setOpaque(false);
        setBackground(null);
        setBounds(0, 0, frameWidth, frameHeight);
        initializeJavaFX(frameWidth, frameHeight);
    }

    private void initializeJavaFX(int frameWidth, int frameHeight) {
        Platform.runLater(() -> {
            writableImage = new WritableImage(frameWidth, frameHeight);
            imageView = new ImageView(writableImage);
            
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, Color.TRANSPARENT);
            setScene(scene);
            
            System.out.println("✅ ContentPanel renderizado como WritableImage.");
        });
    }

    public void updateImage(WritableImage newImage) {
        Platform.runLater(() -> {
            imageView.setImage(newImage);
            System.out.println("🔄 WritableImage atualizado.");
        });
    }
}

