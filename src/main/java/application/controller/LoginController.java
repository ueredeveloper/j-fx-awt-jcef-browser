package application.controller;

import javax.swing.JPanel;

import application.model.Interferencia;
import application.ui.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginController implements Controller {

	@FXML
	private StackPane root;

	@FXML
	private VBox loginBox;

	@FXML
	private TextField passwordField;

	@FXML
	private Button loginButton;

	JPanel contentPanel;

	@FXML
	private void initialize() {

		System.out.println("logint controller initialize");

	}

	@FXML
	private void handleLogin() {
		String password = passwordField.getText();
		System.out.println("Tentativa de login com senha: " + password);
		// Adicione aqui a lógica de autenticação
	}

	@Override
	public void updateCoordinates(Interferencia interference) {
		// TODO Auto-generated method stub
		
	}
}
