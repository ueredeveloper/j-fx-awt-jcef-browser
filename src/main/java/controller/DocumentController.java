package controller;

import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DocumentController {

	@FXML
    private StackPane root;

    @FXML
    private VBox documentBox;

    @FXML
    private ComboBox<?> cbDocumentType;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextField tfNumberSei;

    @FXML
    private ComboBox<?> cbAddress;

    @FXML
    private TextField tfLatitude;

    @FXML
    private TextField tfLongitude;

    @FXML
    private Button btnInterference;

    @FXML
    private ComboBox<?> cbUser;

    @FXML
    private Button btnUser;

    @FXML
    private ComboBox<?> cbProcess;

    @FXML
    private Button btnProcess;

    @FXML
    private ComboBox<?> cbAttachment;

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

	Panel contentPanel;
	JPanel jPanel;
	JFXPanel jfxPanel;



	@FXML
	private void initialize() {
		

	}

}
