package application.utils;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {

	public Animations() {
		super();
	}

	public void shake(Node node) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
		tt.setFromX(0);
		tt.setByX(10);
		tt.setCycleCount(6);
		tt.setAutoReverse(true);
		tt.play();
	}

	public void animatePanel(Panel panel) {
		Timer timer = new Timer(5, new ActionListener() {
			int x = 100; // Posição inicial (10)

			@Override
			public void actionPerformed(ActionEvent e) {
				if (x > 0) { // Garantindo que não ultrapasse o zero
					x -= 1; // Move para a esquerda

					if (x > 50) {
						panel.setLocation(x, panel.getY());
					}

				} else {
					panel.setLocation(50, panel.getY()); // Garante que pare exatamente em 0
					((Timer) e.getSource()).stop(); // Para o timer quando atinge 0
				}
			}
		});
		timer.start();
	}

}
