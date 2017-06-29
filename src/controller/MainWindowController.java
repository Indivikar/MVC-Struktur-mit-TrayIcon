package controller;

//Multi-Controller und Multi-FXMLs

//zuerst im Scene Builder die fxml-Datei includen: File -> Include -> FXML... -> die Datei wählen
//(ACHTUNG! die Datei kann nicht in alle Nodes eingefügt werden, aber man kann sie danach verschieben wo man sie hin haben möchte, es geht auf jeden Fall: AnchorPane, HBox, VBox)
//in der "Main.fxml", in der Zeile "<fx:include fx:id="testTab" source="Test.fxml" />" muss das "fx:id="testTab" von Hand in die Main.fxml eingetragen werden und
//der erste Node von der include fxml muss die id selbe id bekommen

//das example für Multi-Controller und Multi-FXMLs
//https://www.youtube.com/watch?v=osIRfgHTfyg

import controller.ConsoleTabController;
import controller.LoggerTabController;
import controller.TestTabController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.OpenFileOrFolder;
import model.Person;
import model.PfadErmitteln;
import stage.SystemTrayIcon;

public class MainWindowController {

	MainWindowController mainWindowController;

	// Stage
	private SystemTrayIcon main;
	private Stage primaryStage;

	// Models
	public Person person;

	// View's
	@FXML private AnchorPane mainAnchorPane;
	@FXML private TextField textField;
	@FXML private Button button;

	// Included Controller
    @FXML private ConsoleTabController consoleTabController;
    @FXML private LoggerTabController loggerTabController;
    @FXML private TestTabController testTabController;

	// Action's
	@FXML public void actionButton(){
		System.out.println("Button");
		person.setFirstName("Inge");
		person.setLastName("Tenneberg");
		person.setAge("34");
		System.out.println(person.toString());

	}

	public void initialize(){
		this.mainWindowController = this;
		System.out.println("MainController");
		PfadErmitteln pe = new PfadErmitteln(new String[]{"sandmann.jpg"}, false, false);
		textField.setText(pe.getErmittelterFile().toString());
		OpenFileOrFolder open = new OpenFileOrFolder();
		open.mitDesktopMethode(pe.getErmittelterFile());

		consoleTabController.setMainController(this);
        testTabController.setMainController(this);

        testTabController.getLabel().setText("Hallo");
	}

	// Getter
    public TextArea getVisualLog() {return  loggerTabController.getLoggerTxtArea();}


    // Setter
	public void setMainWindowStage(SystemTrayIcon systemTrayIcon, Stage primaryStage){
		this.main = systemTrayIcon;
		this.primaryStage = primaryStage;

		person = new Person();

		System.out.println(person.toString());
	}

}
