package model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stage.SystemTrayIcon;



public class PfadErmitteln {

	private File ermittelterFile;
	private Path ermittelterPath;
	private URI ermittelteURI;
	private URL ermittelteURL;
	
	private String[] dateiUndOrdnerListe;
	
	
	public PfadErmitteln(String[] dateiUndOrdnerListe, boolean isFileImBinOrdner, boolean checkenObFileExists) {

		this.dateiUndOrdnerListe = dateiUndOrdnerListe;
		
		URI pfadURI = null;
		File filePfad;
		
		try {
			pfadURI = SystemTrayIcon.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		if(pfadURI.toString().startsWith("file://")){ // ist es ein UNC-Pfad oder ein Normaler Pfad
			// UNC-Pfad(Netzwerkadresse)
			filePfad = testURL(pfadURI.toString());
		}
		else
		{
			// Normale Pfad(mit einem Buchstaben)
			filePfad = new File( pfadURI );
		}
		if (!isFileImBinOrdner) {
			filePfad = filePfad.getParentFile();
		}
		
		if(!filePfad.exists() && checkenObFileExists){
			alertFilenichtgefunden(filePfad.getAbsolutePath());
		}
		
		for (String string : dateiUndOrdnerListe) {			
			URI ordnerDaten = URI.create(string);
			filePfad = new File(filePfad + File.separator + ordnerDaten);
			
			if(!filePfad.exists() && checkenObFileExists){
				alertFilenichtgefunden(filePfad.getAbsolutePath());
			}
		}

		
		try {
			
			this.ermittelterFile = filePfad;
			this.ermittelterPath = filePfad.toPath();
			this.ermittelteURI = filePfad.toURI();
			this.ermittelteURL = filePfad.toURI().toURL();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    private File testURL(String urlString){
        URL url;
        File file = null;
		try {
			url = new URL(urlString);

	        System.out.println("URL is: " + url.toString());

	        URI uri = url.toURI();
	        System.out.println("URI is: " + uri.toString());

	        if(uri.getAuthority() != null && uri.getAuthority().length() > 0) {
	            // Hack for UNC Path
	            uri = (new URL("file://" + urlString.substring("file:".length()))).toURI();
	        }

	        file = new File(uri);
	        System.out.println("File is: " + file.toString());

	        String parent = file.getParent();
	        System.out.println("Parent is: " + parent);

	        System.out.println("____________________________________________________________");

		} catch (MalformedURLException | URISyntaxException e) {
			System.err.println("URISyntaxException");
			e.printStackTrace();
		}

		return file;
    }

    public void alertFilenichtgefunden(String name){
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Warnung");
    	alert.setHeaderText(null);
    	alert.setContentText("Datei \"" + name + "\" konnte nicht gefunden werden!");

    	alert.show();
    }
    
	public File getErmittelterFile() {
		return ermittelterFile;
	}

	public Path getErmittelterPath() {
		return ermittelterPath;
	}


	public URI getErmittelteURI() {
		return ermittelteURI;
	}


	public URL getErmittelteURL() {
		return ermittelteURL;
	}

}
