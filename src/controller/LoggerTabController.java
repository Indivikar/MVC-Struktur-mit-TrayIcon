package controller;

//Multi-Controller und Multi-FXMLs

//zuerst im Scene Builder die fxml-Datei includen: File -> Include -> FXML... -> die Datei wählen
//(ACHTUNG! die Datei kann nicht in alle Nodes eingefügt werden, aber man kann sie danach verschieben wo man sie hin haben möchte, es geht auf jeden Fall: AnchorPane, HBox, VBox)
//in der "Main.fxml", in der Zeile "<fx:include fx:id="testTab" source="Test.fxml" />" muss das "fx:id="testTab" von Hand in die Main.fxml eingetragen werden und
//der erste Node von der include fxml muss die id selbe id bekommen

//das example für Multi-Controller und Multi-FXMLs
//https://www.youtube.com/watch?v=osIRfgHTfyg

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class LoggerTabController {

	private MainWindowController mainWindowController;

	// View's
    @FXML private VBox loggerTab;
    @FXML private TextArea loggerTxtArea;

    // Action's

    public void initialize() {
    	System.out.println("LoggerTabController");
    }

    // Getter
    public TextArea getLoggerTxtArea() {return loggerTxtArea;}

    // Setter
    public void setMainController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }
}
