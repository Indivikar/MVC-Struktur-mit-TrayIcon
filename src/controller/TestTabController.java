package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

//Multi-Controller und Multi-FXMLs

//zuerst im Scene Builder die fxml-Datei includen: File -> Include -> FXML... -> die Datei wählen
//(ACHTUNG! die Datei kann nicht in alle Nodes eingefügt werden, aber man kann sie danach verschieben wo man sie hin haben möchte, es geht auf jeden Fall: AnchorPane, HBox, VBox)
//in der "Main.fxml", in der Zeile "<fx:include fx:id="testTab" source="Test.fxml" />" muss das "fx:id="testTab" von Hand in die Main.fxml eingetragen werden und
//der erste Node von der include fxml muss die id selbe id bekommen

//das example für Multi-Controller und Multi-FXMLs
//https://www.youtube.com/watch?v=osIRfgHTfyg

public class TestTabController {

	private MainWindowController mainWindowController;

	// View's
    @FXML private Label label;

    // Action's
    @FXML private void actionButton(ActionEvent event) {
    	label.setText("Was");
    }

    public void setMainController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    public void initialize() {
    	System.out.println("TestTabController");
    	label.setText("?");


    }



    // Getter
    public final Label getLabel() {return label;}

    // Setter
    public final void setLabel(Label label) {this.label = label;}

}
