package ui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.ContactManager;
import model.Person;

public class ContactManagerGUI {
	
	@FXML
	private Pane welcomePane;
	
	 @FXML
	 private TextField txtName;

    @FXML
    private TextField txtEmail;
    
    @FXML
    private TableView<Person> tableView;
	
	@FXML
    private TableColumn<Person,String> columNumber;

    @FXML
    private TableColumn<Person,String> columName;

    @FXML
    private TableColumn<Person,String> columEmail;
	
	private ContactManager contactManager;
	
	public ContactManagerGUI(ContactManager contactManager) {
		this.contactManager=contactManager;
	}
	
	@FXML
    public void showAddContact(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddContact.fxml"));
		fxmlLoader.setController(this); //hace referencia al objeto que ha sido creado, en main
		
		Parent addC= fxmlLoader.load();
		welcomePane.getChildren().setAll(addC);	
		
    }

    @FXML
    public void showListContact(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListContact.fxml"));

		fxmlLoader.setController(this);
    	Parent listC= fxmlLoader.load();
		welcomePane.getChildren().setAll(listC);
		initializeTable();
    }
    
    public void selectSave(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Contact Manager");
		alert.setHeaderText(null);
    	if(!txtName.getText().equals("") && !txtEmail.getText().equals("")) {
	    	
    		contactManager.addContact(txtName.getText(), txtEmail.getText());
    		
    		alert.setContentText("The contact has been saved!");

    		alert.showAndWait();

	    	
    	}
    	else {
    		alert.setContentText("You must fill each field in the form");

    		alert.showAndWait();
    	}
    	txtName.clear();
    	txtEmail.clear();
       
    }
    
    public void initializeTable() {
    	ObservableList<Person> list= FXCollections.observableArrayList(contactManager.getContacts());
    	
    	tableView.setItems(list);
    	columName.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));//busca al getName
    	columEmail.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));
    	columNumber.setCellValueFactory(new PropertyValueFactory<Person,String>("numb"));
    }
    
    @FXML
    public void exportContacts(ActionEvent event) {

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File f=fileChooser.showSaveDialog(welcomePane.getScene().getWindow());
    	if(f!=null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Export Contacts");
    		
    		try {
    			contactManager.exportContacts(f.getAbsolutePath());
        		alert.setContentText("The contact data was exported succesfully");
        		alert.showAndWait();
    		}catch(FileNotFoundException e){
        		alert.setContentText("The contact data was not exported. An error occurred");
        		alert.showAndWait();
    		}
    	}
    }

    @FXML
    public void importContacts(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File f=fileChooser.showOpenDialog(welcomePane.getScene().getWindow());
    	if(f!=null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Import Contacts");
    		
    		try {
    			
    			contactManager.importContacts(f.getAbsolutePath());
        		alert.setContentText("The contact data was imported succesfully");
        		alert.showAndWait();
    		}catch(IOException e){
        		alert.setContentText("The contact data was not imported. An error occurred");
        		alert.showAndWait();
    		}
    	}
    }
    
    @FXML
    public void showAbout(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Contact Manager");
		alert.setHeaderText("Credits");
		alert.setContentText("Juan Reyes\nAngélica Corrales\nAlgorithms II");

		alert.showAndWait();
    }

}
