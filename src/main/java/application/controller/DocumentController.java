package application.controller;

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.model.Interferencia;
import application.ui.BrowserPanel;
import application.ui.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DocumentController implements Controller {

	@FXML
	private StackPane root;

	@FXML
	private VBox documentBox;

	@FXML
	private JFXComboBox<?> cbDocumentType;

	@FXML
	private JFXTextField tfNumber;

	@FXML
	private JFXTextField tfNumberSei;

	@FXML
	private JFXComboBox<?> cbAddress;

	@FXML
	private JFXTextField tfLatitude;

	@FXML
	private JFXTextField tfLongitude;

	@FXML
	private Button btnInterference;

	@FXML
	private JFXComboBox<?> cbUser;

	@FXML
	private Button btnUser;

	@FXML
	private JFXComboBox<?> cbProcess;

	@FXML
	private Button btnProcess;

	@FXML
	private JFXComboBox<?> cbAttachment;

	@FXML
	private Button btnAttachment;

	@FXML
	private Button btnUser1;

	@FXML
	private Button btnNew;

	@FXML
	private Button btnEdit;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnSave;

	@FXML
	private TableView<?> tableView;

	@FXML
	private Button btnViewDocument;
	
	BrowserPanel browser;


	public DocumentController(BrowserPanel browser) {
		super();
		this.browser = browser;
	}

	@FXML
	private void initialize() {

		btnSave.setOnAction(event -> {
			System.out.println("clicked save button");
		});

	}

	@Override
	public void updateCoordinates(Interferencia interference) {
		tfLatitude.setText(interference.getLatitude());
		tfLongitude.setText(interference.getLongitude());
		
	}



}
